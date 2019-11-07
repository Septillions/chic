package com.github.chic.admin.component;

import com.github.chic.common.component.JsonResult;
import com.github.chic.common.component.ResultCode;
import com.github.chic.common.exception.AuthException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public JsonResult exception(HttpServletRequest request, Exception e) {
        System.out.println("GlobalExceptionHandler:");
        e.printStackTrace();
        return JsonResult.failed(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public JsonResult authenticationException(HttpServletRequest request, AuthenticationException e) {
        return JsonResult.failed(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public JsonResult accessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return JsonResult.failed(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
    }

    @ExceptionHandler(AuthException.class)
    public JsonResult authException(HttpServletRequest request, AuthException e) {
        return JsonResult.failed(e.getErrCode(), e.getErrMsg());
    }
}
