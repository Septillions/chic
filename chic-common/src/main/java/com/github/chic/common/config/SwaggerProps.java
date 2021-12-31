package com.github.chic.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger 配置参数类
 */
@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProps {
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 版本
     */
    private String version;
    /**
     * 作者
     */
    private Contact contact;

    @Data
    public static class Contact {
        /**
         * 名字
         */
        private String name;
        /**
         * 主页
         */
        private String url;
        /**
         * 邮箱
         */
        private String email;
    }
}
