package com.github.chic.common.component.constant;

/**
 * 全局 API 返回状态码
 */
public enum BaseApiCodeEnum {

    SUCCESS(0, "ok"),
    FAILED(-1, "error"),
    UNAUTHORIZED(11, "未登录"),
    FORBIDDEN(12, "无权限"),
    INVALID(13, "未认证"),
    TOKEN_EXPIRED(14, "Token 失效"),
    TOKEN_INVALID(15, "Token 无效"),
    VALIDATE_FAILED(20, "参数校验失败"),
    ;

    private final Integer code;
    private final String msg;

    BaseApiCodeEnum(Integer code, String msg) {
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
