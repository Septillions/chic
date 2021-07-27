package com.github.chic.admin.component.security.component;

import cn.hutool.extra.spring.SpringUtil;
import com.github.chic.admin.service.PermissionService;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.entity.Permission;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Spring Security 方法安全表达式根类
 */
public class JwtMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final PermissionService permissionService = SpringUtil.getBean(PermissionService.class);

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public JwtMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasPermission(String permission) {
        List<Permission> list = permissionService.listByAdminId(SecurityUtils.getCurrentAdminId());
        for (Permission p : list) {
            if (p.getCode().equals(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }

    public void setThis(Object target) {
        this.target = target;
    }
}
