package com.github.chic.app.component.exception;

import com.github.chic.app.component.constant.ApiCodeEnum;
import com.github.chic.common.component.exception.BaseException;

/**
 * 自定义 API 接口异常
 */
public class ApiException extends BaseException {

    public ApiException(ApiCodeEnum apiCodeEnum) {
        super(apiCodeEnum.getCode(), apiCodeEnum.getMsg());
    }

}
