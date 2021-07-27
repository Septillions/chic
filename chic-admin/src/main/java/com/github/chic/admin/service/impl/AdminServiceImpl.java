package com.github.chic.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.component.constant.RedisKeyCacheEnum;
import com.github.chic.admin.mapper.AdminMapper;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.service.PermissionService;
import com.github.chic.admin.service.RoleService;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RedisService redisService;

    @Override
    public Admin getByUsername(String username) {
        // Redis Key
        String key = RedisKeyCacheEnum.ADMIN_CACHE_ADMIN_PREFIX.getKey() + username;
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
    public List<Permission> listPermissionByAdminId(Long adminId) {
        return permissionService.listByAdminId(adminId);
    }
}
