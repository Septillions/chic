package com.github.chic.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RefreshParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.RefreshVO;
import com.github.chic.admin.service.AdminService;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.entity.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "Auth 授权")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AdminService adminService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody @Valid RegisterParam registerParam) {
        adminService.register(registerParam);
        return ApiResult.success();
    }

    @ApiOperation("登陆")
    @PostMapping("/login")
    public ApiResult<LoginVO> login(@RequestBody @Valid LoginParam loginParam) {
        LoginVO loginVO = adminService.login(loginParam);
        return ApiResult.success(loginVO);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public ApiResult<Object> logout(HttpServletRequest request) {
        String token = request.getHeader(JwtProps.header);
        if (StrUtil.isNotBlank(token)) {
            adminService.logout(token);
        }
        return ApiResult.success();
    }

    @ApiOperation("刷新")
    @PostMapping("/refresh")
    public ApiResult<RefreshVO> refresh(@RequestBody @Valid RefreshParam refreshParam) {
        RefreshVO refreshVO = adminService.refresh(refreshParam);
        return ApiResult.success(refreshVO);
    }

    @ApiOperation("Token测试")
    @PostMapping("/test/token")
    public ApiResult<Object> testToken() {
        return ApiResult.success();
    }

    @ApiOperation("角色测试")
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/test/role")
    public ApiResult<Object> testRole() {
        return ApiResult.success();
    }
}
