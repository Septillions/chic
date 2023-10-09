package com.github.chic.admin.component.handler;

import com.github.chic.admin.component.exception.ApiException;
import com.github.chic.common.component.handler.BaseExceptionHandler;
import com.github.chic.common.model.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 项目模块异常处理类
 */
@Slf4j
@RestControllerAdvice
public class ProjectExceptionHandler extends BaseExceptionHandler {
    /**
     * 自定义 API 接口异常
     */
    @ExceptionHandler(ApiException.class)
    public ApiResult<Object> apiException(ApiException e) {
        return ApiResult.failed(e.getErrCode(), e.getErrMsg());
    }
}
