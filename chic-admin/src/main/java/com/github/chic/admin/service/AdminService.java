package com.github.chic.admin.service;

import com.github.chic.admin.model.dto.AdminParam;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;

import java.util.List;

public interface AdminService {
    void register(AdminParam adminParam);

    String login(AdminParam adminParam);

    Admin getByUsername(String username);

    List<Role> listRoleByAdminId(Integer adminId);

    List<Permission> listPermissionByAdminId(Integer adminId);
}
