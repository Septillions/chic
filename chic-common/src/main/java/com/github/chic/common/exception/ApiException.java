package com.github.chic.common.exception;

/**
 * 自定义 API接口异常
 */
public class ApiException extends RuntimeException {
    private Integer errCode;
    private String errMsg;

    public ApiException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
