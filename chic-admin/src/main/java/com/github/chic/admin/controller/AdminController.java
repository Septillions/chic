package com.github.chic.admin.controller;

import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.service.AdminService;
import com.github.chic.common.component.JsonResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    /**
     * 注册
     */
    @PostMapping("/register")
    public JsonResult register(@RequestBody @Valid RegisterParam registerParam) {
        adminService.register(registerParam);
        return JsonResult.success();
    }

    /**
     * 登陆
     */
    @PostMapping("/login")
    public JsonResult login(@RequestBody @Valid LoginParam loginParam) {
        String token = adminService.login(loginParam);
        Map<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("token", token);
        return JsonResult.success(resultMap);
    }

    /**
     * Token测试
     */
    @PostMapping("/test/token")
    public JsonResult testToken() {
        return JsonResult.success();
    }

    /**
     * 角色测试
     */
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/test/role")
    public JsonResult testRole() {
        return JsonResult.success();
    }

    /**
     * 权限测试
     */
    @PreAuthorize("hasPermission('/test/permission','test:permissions')")
    @PostMapping("/test/permission")
    public JsonResult testPermission() {
        return JsonResult.success();
    }
}
