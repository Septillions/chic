package com.github.chic.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;
import com.github.chic.entity.User;
import com.github.chic.portal.model.param.LoginParam;
import com.github.chic.portal.model.param.RefreshParam;
import com.github.chic.portal.model.param.RegisterParam;
import com.github.chic.portal.model.vo.LoginVO;
import com.github.chic.portal.model.vo.RefreshVO;

import java.util.List;

public interface UserService extends IService<User> {
    void register(RegisterParam registerParam);

    LoginVO login(LoginParam loginParam);

    void logout(String token);

    RefreshVO refresh(RefreshParam refreshParam);

    User getByMobile(String mobile);

    List<Role> listRoleByUserId(Integer userId);

    List<Permission> listPermissionByUserId(Integer userId);
}
