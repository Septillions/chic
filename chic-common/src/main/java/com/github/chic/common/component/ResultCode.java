package com.github.chic.common.component;

/**
 * 接口统一返回状态码
 */
public enum ResultCode {
    SUCCESS(0, "ok"),
    UNAUTHORIZED(1001, "未登录"),
    FORBIDDEN(1002, "无权限"),
    FAILED(5000, "服务器异常");
    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
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
