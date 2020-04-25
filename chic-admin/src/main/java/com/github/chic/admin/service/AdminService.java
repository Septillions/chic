package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;

import java.util.List;

public interface AdminService extends IService<Admin> {
    void register(RegisterParam registerParam);

    String login(LoginParam loginParam);

    void logout(String token);

    Admin getByUsername(String username);

    List<Role> listRoleByAdminId(Integer adminId);

    List<Permission> listPermissionByAdminId(Integer adminId);
}
