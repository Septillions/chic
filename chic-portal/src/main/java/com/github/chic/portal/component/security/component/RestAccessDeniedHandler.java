package com.github.chic.portal.component.security.component;

import com.github.chic.common.component.constant.ApiCodeEnum;
import com.github.chic.common.util.ServletUtils;
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
        ServletUtils.writeJson(response, ApiCodeEnum.FORBIDDEN.getCode(), ApiCodeEnum.FORBIDDEN.getMsg());
    }
}
