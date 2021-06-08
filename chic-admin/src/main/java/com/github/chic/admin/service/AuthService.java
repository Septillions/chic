package com.github.chic.admin.service;

import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RefreshParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.RefreshVO;

public interface AuthService {
    void register(RegisterParam registerParam);

    LoginVO login(LoginParam loginParam);

    void logout(String token);

    RefreshVO refresh(RefreshParam refreshParam);
}
