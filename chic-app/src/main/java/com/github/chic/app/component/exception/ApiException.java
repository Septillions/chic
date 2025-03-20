package com.github.chic.app.component.exception;


import com.github.chic.app.component.constant.ApiCodeEnum;

/**
 * 自定义 API 接口异常
 */
public class ApiException extends RuntimeException {
    private final Integer errCode;
    private final String errMsg;

    public ApiException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ApiException(ApiCodeEnum apiCodeEnum) {
        super(apiCodeEnum.getMsg());
        this.errCode = apiCodeEnum.getCode();
        this.errMsg = apiCodeEnum.getMsg();
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
