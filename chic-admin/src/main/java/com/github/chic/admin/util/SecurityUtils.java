package com.github.chic.admin.util;

import com.github.chic.admin.component.security.entity.JwtAdminDetails;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security 工具类
 */
public class SecurityUtils {
    /**
     * 获取当前用户
     */
    public static JwtAdminDetails getCurrentAdmin() {
        return (JwtAdminDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    /**
     * 获取当前用户ID
     */
    public static Long getCurrentAdminId() {
        return getCurrentAdmin().getId();
    }

    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        return getCurrentAdmin().getUsername();
    }
}
