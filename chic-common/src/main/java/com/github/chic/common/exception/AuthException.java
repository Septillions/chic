package com.github.chic.common.exception;

/**
 * 自定义 认证相关异常
 * errCode 1xxx 格式
 */
public class AuthException extends ApiException {
    public AuthException(Integer errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
