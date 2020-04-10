package com.github.chic.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 认证配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProps {
    /**
     * 不需要认证的接口
     */
    private AuthIgnoreProps ignores;
}
