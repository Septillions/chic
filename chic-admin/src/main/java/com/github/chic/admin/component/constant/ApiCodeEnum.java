package com.github.chic.admin.component.constant;

/**
 * API 返回状态码
 */
public enum ApiCodeEnum {
    AUTH_USERNAME_EXIST(1001, "用户名已经注册"),
    AUTH_USERNAME_NOT_EXIST(1002, "该帐号不存在"),
    AUTH_PASSWORD_ERROR(1003, "帐号或密码错误"),
    AUTH_STATUS_ERROR(1004, "该帐号已被限制登录"),
    AUTH_CAPTCHA_ERROR(1005, "验证码不正确"),
    AUTH_RESET_PASSWORD_ERROR(1006, "原账号密码错误"),
    ROLE_CODE_EXIST(1101, "角色代码已存在"),
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
