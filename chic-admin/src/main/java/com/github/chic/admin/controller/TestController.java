package com.github.chic.admin.controller;

import com.github.chic.common.model.api.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Test 测试")
@RestController
@RequestMapping("/test")
public class TestController {
    @ApiOperation("Token测试")
    @PostMapping("/token")
    public ApiResult<Object> testToken() {
        return ApiResult.success();
    }

    @ApiOperation("角色测试")
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/role")
    public ApiResult<Object> testRole() {
        return ApiResult.success();
    }

    @ApiOperation("权限测试")
    @PreAuthorize("hasPermission('user:list:view')")
    @PostMapping("/permission")
    public ApiResult<Object> testPermission() {
        return ApiResult.success();
    }
}
