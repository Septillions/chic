package com.github.chic.common.exception;

/**
 * 自定义 校验相关异常
 * errCode 2xxx 格式
 */
public class VerifyException extends ApiException {
    public VerifyException(Integer errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
