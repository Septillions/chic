package com.github.chic.common.exception;

/**
 * 自定义 校验相关异常
 * errCode 2xxx 格式
 */
public class VerifyException extends ApiException {
    /**
     * 异常信息前缀
     */
    private static final String ERR_MSG_PREFIX = "校验异常(VerifyException):";

    public VerifyException(Integer errCode, String errMsg) {
        super(errCode, ERR_MSG_PREFIX + errMsg);
    }
}
