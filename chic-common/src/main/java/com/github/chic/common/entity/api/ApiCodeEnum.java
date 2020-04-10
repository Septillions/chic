package com.github.chic.common.entity.api;

/**
 * API 返回状态码
 */
public enum ApiCodeEnum {
    /**
     * 成功
     */
    SUCCESS(0, "ok"),
    /**
     * 未登录
     */
    UNAUTHORIZED(1001, "未登录"),
    /**
     * 无权限
     */
    FORBIDDEN(1002, "无权限"),
    /**
     * 未认证
     */
    INVALID(1003, "未认证"),
    /**
     * 服务器异常
     */
    FAILED(5000, "服务器异常");
    private Integer code;
    private String msg;

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
