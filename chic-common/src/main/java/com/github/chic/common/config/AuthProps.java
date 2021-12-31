package com.github.chic.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Auth 配置参数类
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProps {
    /**
     * 忽略认证的接口
     */
    private Ignore ignore;

    @Data
    public static class Ignore {
        /**
         * 需要忽略的 URL 格式，不考虑请求方法
         */
        private List<String> pattern = new ArrayList<>();
        /**
         * 需要忽略的 GET 请求
         */
        private List<String> get = new ArrayList<>();
        /**
         * 需要忽略的 POST 请求
         */
        private List<String> post = new ArrayList<>();
        /**
         * 需要忽略的 PUT 请求
         */
        private List<String> put = new ArrayList<>();
        /**
         * 需要忽略的 DELETE 请求
         */
        private List<String> delete = new ArrayList<>();
    }
}
