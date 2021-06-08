package com.github.chic.portal.service;

import com.github.chic.portal.model.param.LoginParam;
import com.github.chic.portal.model.param.RefreshParam;
import com.github.chic.portal.model.param.RegisterParam;
import com.github.chic.portal.model.vo.LoginVO;
import com.github.chic.portal.model.vo.RefreshVO;

public interface AuthService {
    void register(RegisterParam registerParam);

    LoginVO login(LoginParam loginParam);

    void logout(String token);

    RefreshVO refresh(RefreshParam refreshParam);
}
