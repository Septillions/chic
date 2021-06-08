package com.github.chic.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.mapper.AdminMapper;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.service.PermissionService;
import com.github.chic.admin.service.RoleService;
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

    @Override
    public Admin getByUsername(String username) {
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.lambda().eq(Admin::getUsername, username);
        return this.baseMapper.selectOne(qw);
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
