package com.github.chic.common.exception;

/**
 * 自定义 认证相关异常
 * errCode 1xxx 格式
 */
public class AuthException extends ApiException {
    /**
     * 异常信息前缀
     */
    private static final String ERR_MSG_PREFIX = "认证异常(AuthException):";

    public AuthException(Integer errCode, String errMsg) {
        super(errCode, ERR_MSG_PREFIX + errMsg);
    }
}
