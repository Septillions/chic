package com.github.chic.app.util;

import com.github.chic.app.component.security.entity.JwtUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security 工具类
 */
public class SecurityUtils {
    /**
     * 获取当前用户
     */
    public static JwtUserDetails getCurrentUser() {
        return (JwtUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
