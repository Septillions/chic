package com.github.chic.portal.security.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chic.common.component.ApiResult;
import com.github.chic.common.component.ApiCodeEnum;
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
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ApiResult apiResult = ApiResult.failed(ApiCodeEnum.UNAUTHORIZED.getCode(), ApiCodeEnum.UNAUTHORIZED.getMsg());
        response.getWriter().println(JSON.toJSONString(apiResult, SerializerFeature.WriteMapNullValue));
        response.getWriter().flush();
    }
}
