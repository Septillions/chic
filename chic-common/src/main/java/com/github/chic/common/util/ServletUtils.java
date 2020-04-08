package com.github.chic.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Servlet 工具类
 */
public class ServletUtils {
    /**
     * 获取请求 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(attributes);
        return attributes.getRequest();
    }

    /**
     * 获取请求 IP
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        return ServletUtil.getClientIP(request);
    }

    /**
     * 获取请求 UserAgent
     */
    public static UserAgent getUserAgent() {
        HttpServletRequest request = getRequest();
        return UserAgentUtil.parse(request.getHeader("User-Agent"));
    }
}
