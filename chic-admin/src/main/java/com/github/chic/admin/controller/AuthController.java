package com.github.chic.admin.controller;

import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.service.AdminService;
import com.github.chic.common.entity.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "Auth 授权")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AdminService adminService;

    /**
     * 注册
     */
    @ApiOperation("注册")
    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody @Valid RegisterParam registerParam) {
        adminService.register(registerParam);
        return ApiResult.success();
    }

    /**
     * 登陆
     */
    @ApiOperation("登陆")
    @PostMapping("/login")
    public ApiResult<LoginVO> login(@RequestBody @Valid LoginParam loginParam) {
        String token = adminService.login(loginParam);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        return ApiResult.success(loginVO);
    }

    /**
     * Token测试
     */
    @PostMapping("/test/token")
    public ApiResult<Object> testToken() {
        return ApiResult.success();
    }

    /**
     * 角色测试
     */
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/test/role")
    public ApiResult<Object> testRole() {
        return ApiResult.success();
    }
}
