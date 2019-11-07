package com.github.chic.admin.component;

import com.github.chic.common.component.JsonResult;
import com.github.chic.common.component.ResultCode;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.exception.ServiceException;
import com.github.chic.common.exception.VerifyException;
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
     * 全局异常 统一JSON格式返回
     */
    @ExceptionHandler(Exception.class)
    public JsonResult exception(HttpServletRequest request, Exception e) {
        System.out.println("GlobalExceptionHandler:");
        e.printStackTrace();
        return JsonResult.failed(e.getMessage());
    }

    /**
     * Spring Security 未登录异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public JsonResult authenticationException(HttpServletRequest request, AuthenticationException e) {
        return JsonResult.failed(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg());
    }

    /**
     * Spring Security 无权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public JsonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return JsonResult.failed(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
    }

    /**
     * 自定义 认证相关异常
     * errCode 1xxx 格式
     */
    @ExceptionHandler(AuthException.class)
    public JsonResult authException(HttpServletRequest request, AuthException e) {
        return JsonResult.failed(e.getErrCode(), e.getErrMsg());
    }

    /**
     * 自定义 校验相关异常
     * errCode 2xxx 格式
     */
    @ExceptionHandler(VerifyException.class)
    public JsonResult verifyException(HttpServletRequest request, VerifyException e) {
        return JsonResult.failed(e.getErrCode(), e.getErrMsg());
    }

    /**
     * 自定义 业务相关异常
     * errCode 3xxx 格式
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult serviceException(HttpServletRequest request, ServiceException e) {
        return JsonResult.failed(e.getErrCode(), e.getErrMsg());
    }
}
