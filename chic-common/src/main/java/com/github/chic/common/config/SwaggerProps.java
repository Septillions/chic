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
     * 文档开关
     */
    private Boolean enable = false;
}
