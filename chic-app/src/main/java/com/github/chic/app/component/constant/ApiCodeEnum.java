package com.github.chic.app.component.constant;

/**
 * API 返回状态码
 */
public enum ApiCodeEnum {
    AUTH_MOBILE_EXIST(1011, "用户名已经注册"),
    AUTH_MOBILE_NOT_EXIST(1012, "该帐号不存在"),
    AUTH_PASSWORD_ERROR(1013, "帐号或密码错误"),
    AUTH_STATUS_ERROR(1014, "该帐号已被限制登录"),
    AUTH_CAPTCHA_ERROR(1015, "验证码不正确"),
    AUTH_RESET_PASSWORD_ERROR(1016, "原账号密码错误"),
    ;
    private final Integer code;
    private final String msg;

    ApiCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
