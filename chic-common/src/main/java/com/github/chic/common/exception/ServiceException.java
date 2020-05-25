package com.github.chic.common.exception;

/**
 * 自定义 业务相关异常
 * errCode 3xxx 格式
 */
public class ServiceException extends ApiException {
    public ServiceException(Integer errCode, String errMsg) {
        super(errCode, errMsg);
    }
}
