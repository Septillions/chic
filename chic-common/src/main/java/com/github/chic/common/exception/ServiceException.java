package com.github.chic.common.exception;

/**
 * 自定义 业务相关异常
 * errCode 3xxx 格式
 */
public class ServiceException extends ApiException {
    /**
     * 异常信息前缀
     */
    private static final String ERR_MSG_PREFIX = "业务异常(ServiceException):";

    public ServiceException(Integer errCode, String errMsg) {
        super(errCode, ERR_MSG_PREFIX + errMsg);
    }
}
