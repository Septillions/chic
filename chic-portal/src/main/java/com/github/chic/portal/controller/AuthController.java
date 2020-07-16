package com.github.chic.portal.controller;

import cn.hutool.core.util.StrUtil;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.portal.model.param.LoginParam;
import com.github.chic.portal.model.param.RefreshParam;
import com.github.chic.portal.model.param.RegisterParam;
import com.github.chic.portal.model.vo.LoginVO;
import com.github.chic.portal.model.vo.RefreshVO;
import com.github.chic.portal.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private UserService userService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody @Valid RegisterParam registerParam) {
        userService.register(registerParam);
        return ApiResult.success();
    }

    @ApiOperation("登陆")
    @PostMapping("/login")
    public ApiResult<LoginVO> login(@RequestBody @Valid LoginParam loginParam) {
        LoginVO loginVO = userService.login(loginParam);
        return ApiResult.success(loginVO);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public ApiResult<Object> logout(HttpServletRequest request) {
        String token = request.getHeader(JwtProps.header);
        if (StrUtil.isNotBlank(token)) {
            userService.logout(token);
        }
        return ApiResult.success();
    }

    @ApiOperation("刷新")
    @PostMapping("/refresh")
    public ApiResult<RefreshVO> refresh(@RequestBody @Valid RefreshParam refreshParam) {
        RefreshVO refreshVO = userService.refresh(refreshParam);
        return ApiResult.success(refreshVO);
    }
}
