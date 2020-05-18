package com.github.chic.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RefreshParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.RefreshVO;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;

import java.util.List;

public interface AdminService extends IService<Admin> {
    void register(RegisterParam registerParam);

    LoginVO login(LoginParam loginParam);

    void logout(String token);

    RefreshVO refresh(RefreshParam refreshParam);

    Admin getByUsername(String username);

    List<Role> listRoleByAdminId(Integer adminId);

    List<Permission> listPermissionByAdminId(Integer adminId);
}
