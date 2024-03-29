package com.github.chic.app.component.security.component;

import com.github.chic.common.component.constant.BaseApiCodeEnum;
import com.github.chic.common.util.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录处理类
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServletUtils.writeJson(response, BaseApiCodeEnum.UNAUTHORIZED.getCode(), BaseApiCodeEnum.UNAUTHORIZED.getMsg());
    }
}
