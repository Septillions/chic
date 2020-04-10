package com.github.chic.portal.component.handler;

import com.github.chic.common.component.ApiResult;
import com.github.chic.common.component.ApiCodeEnum;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.exception.ServiceException;
import com.github.chic.common.exception.VerifyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 全局异常 统一JSON格式返回
     */
    @ExceptionHandler(Exception.class)
    public ApiResult exception(HttpServletRequest request, Exception e) {
        LOGGER.error("RestExceptionHandler:", e);
        return ApiResult.failed(e.getMessage());
    }

    /**
     * Spring Security 未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public ApiResult authenticationException(HttpServletRequest request, AuthenticationException e) {
        return ApiResult.failed(ApiCodeEnum.UNAUTHORIZED.getCode(), ApiCodeEnum.UNAUTHORIZED.getMsg());
    }

    /**
     * Spring Security 无权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return ApiResult.failed(ApiCodeEnum.FORBIDDEN.getCode(), ApiCodeEnum.FORBIDDEN.getMsg());
    }

    /**
     * 自定义 认证相关异常
     * errCode 1xxx 格式
     */
    @ExceptionHandler(AuthException.class)
    public ApiResult authException(HttpServletRequest request, AuthException e) {
        return ApiResult.failed(e.getErrCode(), e.getErrMsg());
    }

    /**
     * 自定义 校验相关异常
     * errCode 2xxx 格式
     */
    @ExceptionHandler(VerifyException.class)
    public ApiResult verifyException(HttpServletRequest request, VerifyException e) {
        return ApiResult.failed(e.getErrCode(), e.getErrMsg());
    }

    /**
     * 自定义 业务相关异常
     * errCode 3xxx 格式
     */
    @ExceptionHandler(ServiceException.class)
    public ApiResult serviceException(HttpServletRequest request, ServiceException e) {
        return ApiResult.failed(e.getErrCode(), e.getErrMsg());
    }
}
