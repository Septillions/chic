package com.github.chic.common.component.exception;

import com.github.chic.common.component.constant.BaseApiCodeEnum;

/**
 * 全局 API 接口异常
 */
public class BaseException extends RuntimeException {
    /**
     * 错误码
     */
    private final Integer errCode;
    /**
     * 错误信息
     */
    private final String errMsg;

    public BaseException(Integer errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BaseException(BaseApiCodeEnum baseApiCodeEnum) {
        super(baseApiCodeEnum.getMsg());
        this.errCode = baseApiCodeEnum.getCode();
        this.errMsg = baseApiCodeEnum.getMsg();
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
