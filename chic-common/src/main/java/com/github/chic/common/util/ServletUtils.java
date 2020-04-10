package com.github.chic.common.util;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chic.common.component.ApiResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Servlet 工具类
 */
public class ServletUtils {

    /**
     * 返回 JSON 格式数据
     */
    public static void writeJson(HttpServletResponse response, Integer code, String msg) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setCharacterEncoding("UTF-8");
        String contentType = "application/json";
        String json = JSON.toJSONString(ApiResult.failed(code, msg), SerializerFeature.WriteMapNullValue);
        ServletUtil.write(response, json, contentType);
    }

    /**
     * 获取请求 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(attributes);
        return attributes.getRequest();
    }

    /**
     * 获取请求 HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Objects.requireNonNull(attributes);
        return attributes.getResponse();
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
