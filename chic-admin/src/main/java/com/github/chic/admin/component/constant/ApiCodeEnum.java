package com.github.chic.admin.component.constant;

/**
 * API 返回状态码
 */
public enum ApiCodeEnum {
    // 公共错误
    COMMON_UNAUTHORIZED(1001, "未登录"),
    COMMON_FORBIDDEN(1002, "无权限"),
    COMMON_PARAM_ERROR(1003, "参数错误"),

    // 认证相关
    AUTH_ACCESS_TOKEN_EXPIRED(2001, "AccessToken 已失效"),
    AUTH_REFRESH_TOKEN_EXPIRED(2002, "RefreshToken 已失效"),
    AUTH_USERNAME_EXIST(2101, "用户名已经注册"),
    AUTH_USERNAME_NOT_EXIST(2102, "该帐号不存在"),
    AUTH_PASSWORD_ERROR(2103, "帐号或密码错误"),
    AUTH_STATUS_ERROR(2104, "该帐号已被限制登录"),
    AUTH_CAPTCHA_ERROR(2105, "验证码不正确"),
    AUTH_RESET_PASSWORD_ERROR(2106, "原账号密码错误"),

    // 第三方服务
    ESCROW_ALIYUN_OSS_ERROR(3011, "阿里云对象存储初始化失败"),
    ESCROW_ALIYUN_STS_ERROR(3011, "获取 OSS 临时访问凭证失败"),

    // 业务相关
    ROLE_CODE_EXIST(4001, "角色代码已存在"),
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
