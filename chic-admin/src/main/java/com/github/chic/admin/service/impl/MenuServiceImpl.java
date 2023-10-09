package com.github.chic.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.component.constant.RedisKeyEnum;
import com.github.chic.admin.dao.MenuDao;
import com.github.chic.admin.model.param.MenuAddParam;
import com.github.chic.admin.model.param.MenuDeleteParam;
import com.github.chic.admin.model.param.MenuUpdateParam;
import com.github.chic.admin.model.query.MenuQuery;
import com.github.chic.admin.model.query.MenuRoleQuery;
import com.github.chic.admin.service.MenuService;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
    @Resource
    private RedisService redisService;

    @Override
    public List<Menu> listByQuery(MenuQuery query) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        if (query.getType() != null) {
            qw.lambda().eq(Menu::getType, query.getType());
        }
        if (query.getStatus() != null) {
            qw.lambda().eq(Menu::getStatus, query.getStatus());
        }
        qw.lambda().orderByAsc(Menu::getSort);
        return super.list(qw);
    }

    @Override
    public void addByParam(MenuAddParam param) {
        Menu menu = BeanUtil.copyProperties(param, Menu.class);
        super.save(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByParam(MenuUpdateParam param) {
        Menu oldData = super.getById(param.getId());
        // 更新菜单
        Menu menu = BeanUtil.copyProperties(param, Menu.class, "sort");
        super.updateById(menu);
        // 判断是否修改了父级
        if (param.getParentId() != null && !oldData.getParentId().equals(param.getParentId())) {
            // 重新排序新父级下的菜单
            this.handleSortRearrange(param.getParentId(), param.getId(), param.getSort());
            // 重新排序旧父级下的菜单
            this.handleSortRearrange(oldData.getParentId(), null, null);
        }
        // 判断是否修改了排序
        else if (param.getSort() != null && !oldData.getSort().equals(param.getSort())) {
            this.handleSortRearrange(oldData.getParentId(), param.getId(), param.getSort());
        }
        // 判断是否修改了状态
        if (param.getStatus() != null && !oldData.getStatus().equals(param.getStatus())) {
            this.handleStatusSubset(menu.getId(), param.getStatus());
        }
        this.clearCache();
    }

    @Override
    public void deleteByParam(MenuDeleteParam param) {
        super.removeById(param.getId());
        this.clearCache();
    }

    @Override
    public List<Menu> listByAdminId(Long adminId) {
        // Redis Key
        String key = RedisKeyEnum.ADMIN_CACHE_MENU_PREFIX.getKey() + adminId;
        // 查询 Redis
        List<Menu> menuList = (List<Menu>) redisService.get(key);
        if (CollUtil.isEmpty(menuList)) {
            // 查询 MySQL
            menuList = this.baseMapper.listByAdminId(adminId);
            // 缓存
            redisService.set(key, menuList, CacheProps.defaultExpireTime);
        }
        return menuList;
    }

    @Override
    public List<Menu> listByRole(MenuRoleQuery query) {
        return this.baseMapper.listByRole(query);
    }

    /**
     * 根据父级ID重新排序
     */
    private void handleSortRearrange(Long parentId, Long id, Integer sort) {
        QueryWrapper<Menu> qw = new QueryWrapper<>();
        qw.lambda().eq(Menu::getParentId, parentId)
                .orderByAsc(Menu::getSort);
        List<Menu> menuList = super.list(qw);
        // 拖拽排序
        if (id != null && sort != null) {
            // 找到需要改变排序的Menu对象
            Menu draggedMenu = menuList.stream()
                    .filter(menu -> menu.getId().equals(id))
                    .findFirst()
                    .get();
            // 移除被拖拽的Menu对象
            menuList.remove(draggedMenu);
            // 在正确的位置插入被拖拽的Menu对象
            menuList.add(sort - 1, draggedMenu);
        }
        // 重新排序
        for (int i = 0; i < menuList.size(); i++) {
            Menu menu = menuList.get(i);
            menu.setSort(i + 1);
        }
        super.updateBatchById(menuList);
    }

    /**
     * 根据ID更新子集状态
     */
    private void handleStatusSubset(Long id, Integer status) {
        List<Menu> menuList = super.list();
        List<Long> subsetIdList = this.getSubsetIdList(menuList, id);
        if (subsetIdList.size() > 0) {
            for (Long subsetId : subsetIdList) {
                Menu menu = menuList.stream()
                        .filter(item -> item.getId().equals(subsetId))
                        .findFirst()
                        .get();
                menu.setStatus(status);
            }
            super.updateBatchById(menuList);
        }
    }

    /**
     * 根据父级ID获取子集ID列表
     */
    private List<Long> getSubsetIdList(List<Menu> menuList, Long parentId) {
        List<Long> subsetIdList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId().equals(parentId)) {
                subsetIdList.add(menu.getId());
                subsetIdList.addAll(getSubsetIdList(menuList, menu.getId()));
            }
        }
        return subsetIdList;
    }

    /**
     * 清空 Redis 菜单缓存
     */
    public void clearCache() {
        // Redis Key
        Set<String> keys = redisService.keys(RedisKeyEnum.ADMIN_CACHE_MENU_PREFIX.getKey() + "*");
        // 删除所有缓存
        redisService.delete(keys);
    }
}
