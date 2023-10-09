package com.github.chic.app.service;

import com.github.chic.app.model.param.LoginParam;
import com.github.chic.app.model.param.RefreshParam;
import com.github.chic.app.model.param.RegisterParam;
import com.github.chic.app.model.vo.LoginVO;
import com.github.chic.app.model.vo.RefreshVO;

public interface AuthService {
    void register(RegisterParam param);

    LoginVO login(LoginParam param);

    void logout(String token);

    RefreshVO refresh(RefreshParam param);
}
