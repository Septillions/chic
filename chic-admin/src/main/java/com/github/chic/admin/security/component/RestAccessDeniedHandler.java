package com.github.chic.admin.security.component;

import com.github.chic.common.component.ResultCode;
import com.github.chic.common.util.JsonResultUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限处理类
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        JsonResultUtils.responseJson(response, ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMsg());
    }
}
