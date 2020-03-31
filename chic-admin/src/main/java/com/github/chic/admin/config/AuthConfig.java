package com.github.chic.admin.config;

import com.github.chic.admin.security.entity.IgnoreConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {
    /**
     * 不需要认证的接口
     */
    private IgnoreConfig ignores;
}
