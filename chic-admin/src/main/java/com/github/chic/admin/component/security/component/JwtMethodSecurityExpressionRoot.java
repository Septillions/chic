package com.github.chic.admin.component.security.component;

import cn.hutool.extra.spring.SpringUtil;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.entity.Menu;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Spring Security 方法安全表达式根类
 */
public class JwtMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final AdminService adminService = SpringUtil.getBean(AdminService.class);

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public JwtMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    public boolean hasPermission(String permission) {
        List<Menu> list = adminService.listMenuByAdminId(SecurityUtils.getCurrentAdminId());
        for (Menu menu : list) {
            if (permission.equals(menu.getCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
}
