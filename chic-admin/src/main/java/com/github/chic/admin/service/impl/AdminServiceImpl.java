package com.github.chic.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.component.constant.RedisKeyEnum;
import com.github.chic.admin.model.param.AdminAddParam;
import com.github.chic.admin.model.param.AdminDeleteParam;
import com.github.chic.admin.model.param.AdminUpdateParam;
import com.github.chic.admin.model.query.AdminQuery;
import com.github.chic.admin.service.AdminRoleRelationService;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.service.MenuService;
import com.github.chic.admin.service.RoleService;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.model.param.PageQuery;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.Admin;
import com.github.chic.entity.AdminRoleRelation;
import com.github.chic.entity.Menu;
import com.github.chic.entity.Role;
import com.github.chic.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private AdminRoleRelationService adminRoleRelationService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisService redisService;

    @Override
    public List<Admin> pageQuery(PageQuery page, AdminQuery query) {
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        if (StrUtil.isNotBlank(query.getUsername())) {
            qw.lambda().like(Admin::getUsername, query.getUsername());
        }
        if (StrUtil.isNotBlank(query.getMobile())) {
            qw.lambda().like(Admin::getMobile, query.getMobile());
        }
        if (query.getStatus() != null) {
            qw.lambda().eq(Admin::getStatus, query.getStatus());
        }
        PageHelper.startPage(page.getPageIndex(), page.getPageSize(), page.getSort());
        return super.list(qw);
    }

    @Override
    public void addByParam(AdminAddParam param) {
        Admin admin = BeanUtil.copyProperties(param, Admin.class);
        admin.setNickname(param.getUsername());
        admin.setPassword(passwordEncoder.encode(param.getPassword()));
        super.save(admin);
        // 关联角色权限
        List<AdminRoleRelation> relationList = new ArrayList<>();
        for (Long roleId : param.getRoleIdList()) {
            AdminRoleRelation relation = new AdminRoleRelation();
            relation.setAdminId(admin.getId());
            relation.setRoleId(roleId);
            relationList.add(relation);
        }
        adminRoleRelationService.saveBatch(relationList);
    }

    @Override
    public void updateByParam(AdminUpdateParam param) {
        Admin admin = BeanUtil.copyProperties(param, Admin.class);
        if (StrUtil.isNotBlank(param.getPassword())) {
            admin.setPassword(passwordEncoder.encode(param.getPassword()));
        }
        if (CollUtil.isNotEmpty(param.getRoleIdList())) {
            List<AdminRoleRelation> relationList = new ArrayList<>();
            for (Long roleId : param.getRoleIdList()) {
                AdminRoleRelation relation = new AdminRoleRelation();
                relation.setAdminId(admin.getId());
                relation.setRoleId(roleId);
                relationList.add(relation);
            }
            adminRoleRelationService.deleteByAdminId(admin.getId());
            adminRoleRelationService.saveBatch(relationList);
        }
        super.updateById(admin);
        this.clearCacheByUsername(SecurityUtils.getCurrentUsername());
    }

    @Override
    public void deleteByParam(AdminDeleteParam param) {
        super.removeById(param.getId());
        this.clearCacheByUsername(SecurityUtils.getCurrentUsername());
    }

    @Override
    public Admin getByUsername(String username) {
        // Redis Key
        String key = RedisKeyEnum.ADMIN_CACHE_ADMIN_PREFIX.getKey() + username;
        // 查询 Redis
        Admin admin = (Admin) redisService.get(key);
        if (admin == null) {
            // 查询 MySQL
            QueryWrapper<Admin> qw = new QueryWrapper<>();
            qw.lambda().eq(Admin::getUsername, username);
            admin = this.baseMapper.selectOne(qw);
            // 缓存
            redisService.set(key, admin, CacheProps.defaultExpireTime);
        }
        return admin;
    }

    @Override
    public List<Role> listRoleByAdminId(Long adminId) {
        return roleService.listByAdminId(adminId);
    }

    @Override
    public List<Menu> listMenuByAdminId(Long adminId) {
        return menuService.listByAdminId(adminId);
    }

    private void clearCacheByUsername(String username) {
        // Redis Key
        String key = RedisKeyEnum.ADMIN_CACHE_ADMIN_PREFIX.getKey() + username;
        // 删除缓存
        redisService.delete(key);
    }
}
