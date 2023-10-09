package com.github.chic.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.component.constant.ApiCodeEnum;
import com.github.chic.admin.component.constant.RedisKeyEnum;
import com.github.chic.admin.component.exception.ApiException;
import com.github.chic.admin.dao.RoleDao;
import com.github.chic.admin.model.param.RoleAddParam;
import com.github.chic.admin.model.param.RoleDeleteParam;
import com.github.chic.admin.model.param.RoleUpdateParam;
import com.github.chic.admin.model.query.RoleQuery;
import com.github.chic.admin.service.RoleMenuRelationService;
import com.github.chic.admin.service.RoleService;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.Role;
import com.github.chic.entity.RoleMenuRelation;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Resource
    private RedisService redisService;
    @Resource
    private RoleMenuRelationService roleMenuRelationService;

    @Override
    public List<Role> pageQuery(PageQuery page, RoleQuery query) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        if (query.getStatus() != null) {
            qw.lambda().eq(Role::getStatus, query.getStatus());
        }
        PageHelper.startPage(page.getPageIndex(), page.getPageSize());
        return super.list(qw);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addByParam(RoleAddParam param) {
        QueryWrapper<Role> qw = new QueryWrapper<>();
        qw.lambda().eq(Role::getCode, param.getCode());
        int count = super.count(qw);
        if (count > 0) {
            throw new ApiException(ApiCodeEnum.ROLE_CODE_EXIST);
        }
        Role role = BeanUtil.copyProperties(param, Role.class);
        super.save(role);
        // 关联菜单权限
        List<RoleMenuRelation> relationList = new ArrayList<>();
        for (Long menuId : param.getMenuIdList()) {
            RoleMenuRelation relation = new RoleMenuRelation();
            relation.setRoleId(role.getId());
            relation.setMenuId(menuId);
        }
        roleMenuRelationService.saveBatch(relationList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateByParam(RoleUpdateParam param) {
        Role role = BeanUtil.copyProperties(param, Role.class);
        if (CollUtil.isNotEmpty(param.getMenuIdList())) {
            List<RoleMenuRelation> relationList = new ArrayList<>();
            for (Long menuId : param.getMenuIdList()) {
                RoleMenuRelation relation = new RoleMenuRelation();
                relation.setRoleId(role.getId());
                relation.setMenuId(menuId);
                relationList.add(relation);
            }
            roleMenuRelationService.deleteByRoleId(role.getId());
            roleMenuRelationService.saveBatch(relationList);
        }
        super.updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByParam(RoleDeleteParam param) {
        super.removeById(param.getId());
        roleMenuRelationService.deleteByRoleId(param.getId());
    }

    @Override
    public List<Role> listByAdminId(Long adminId) {
        // Redis Key
        String key = RedisKeyEnum.ADMIN_CACHE_ROLE_PREFIX.getKey() + adminId;
        // 查询 Redis
        List<Role> roleList = (List<Role>) redisService.get(key);
        if (CollUtil.isEmpty(roleList)) {
            // 查询 MySQL
            roleList = this.baseMapper.listByAdminId(adminId);
            // 缓存
            redisService.set(key, roleList, CacheProps.defaultExpireTime);
        }
        return roleList;
    }
}
