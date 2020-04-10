package com.github.chic.portal.security.component;

import com.github.chic.entity.Permission;
import com.github.chic.portal.security.entity.JwtUserDetails;
import com.github.chic.portal.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义权限注解验证
 */
@Component
public class JwtPermissionEvaluator implements PermissionEvaluator {
    @Resource
    private UserService userService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permissionCode) {
        // 获取用户信息
        JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
        // 获取用户权限
        List<Permission> permissionList = userService.listPermissionByUserId(jwtUserDetails.getUserId());
        Set<String> permissions = new HashSet<>();
        for (Permission permission : permissionList) {
            permissions.add(permission.getPermissionCode());
        }
        // 权限验证
        return permissions.contains(permissionCode.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
