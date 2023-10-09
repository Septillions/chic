package com.github.chic.admin.service;

import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.PasswordResetParam;
import com.github.chic.admin.model.param.RefreshTokenParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.CaptchaVO;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.RefreshTokenVO;

public interface AuthService {
    CaptchaVO captcha();

    void register(RegisterParam param);

    LoginVO login(LoginParam param);

    void logout(String token);

    RefreshTokenVO refreshToken(RefreshTokenParam param);

    void resetPassword(PasswordResetParam param);
}
