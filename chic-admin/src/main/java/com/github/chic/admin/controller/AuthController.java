package com.github.chic.admin.controller;

import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.service.AdminService;
import com.github.chic.common.component.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "Auth 授权")
@RestController
@RequestMapping("/admin/auth")
public class AuthController {
    @Resource
    private AdminService adminService;

    /**
     * 注册
     */
    @ApiOperation("注册")
    @PostMapping("/register")
    public JsonResult register(@RequestBody @Valid RegisterParam registerParam) {
        adminService.register(registerParam);
        return JsonResult.success();
    }

    /**
     * 登陆
     */
    @ApiOperation("登陆")
    @PostMapping("/login")
    public JsonResult<LoginVO> login(@RequestBody @Valid LoginParam loginParam) {
        String token = adminService.login(loginParam);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        return JsonResult.success(loginVO);
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
    @PreAuthorize("hasPermission('/test/permission','test:permission')")
    @PostMapping("/test/permission")
    public JsonResult testPermission() {
        return JsonResult.success();
    }
}
