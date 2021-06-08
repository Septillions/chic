package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;

import java.util.List;

public interface AdminService extends IService<Admin> {
    Admin getByUsername(String username);

    List<Role> listRoleByAdminId(Integer adminId);

    List<Permission> listPermissionByAdminId(Integer adminId);
}
