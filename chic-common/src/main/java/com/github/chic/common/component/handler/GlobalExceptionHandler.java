package com.github.chic.common.component.handler;

import com.github.chic.common.entity.api.ApiCodeEnum;
import com.github.chic.common.entity.api.ApiResult;
import com.github.chic.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理类
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常 统一JSON格式返回
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<Object> exception(Exception e) {
        log.error("服务器未知异常", e);
        return ApiResult.failed(e.getMessage());
    }

    /**
     * Spring Security 未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public ApiResult<Object> authenticationException(AuthenticationException e) {
        return ApiResult.failed(ApiCodeEnum.UNAUTHORIZED.getCode(), ApiCodeEnum.UNAUTHORIZED.getMsg());
    }

    /**
     * Spring Security 无权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResult<Object> accessDeniedException(AccessDeniedException e) {
        return ApiResult.failed(ApiCodeEnum.FORBIDDEN.getCode(), ApiCodeEnum.FORBIDDEN.getMsg());
    }

    /**
     * Spring Validation 校验异常
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public ApiResult<Object> validationException(Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorMsg = new StringBuilder("校验异常(VerifyException):");
        for (FieldError error : fieldErrors) {
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(",");
        }
        errorMsg.deleteCharAt(errorMsg.length() - 1);
        return ApiResult.failed(2001, errorMsg.toString());
    }

    /**
     * 自定义 API接口异常
     */
    @ExceptionHandler(ApiException.class)
    public ApiResult<Object> apiException(ApiException e) {
        return ApiResult.failed(e.getErrCode(), e.getErrMsg());
    }
}
