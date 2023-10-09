package com.github.chic.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.PasswordResetParam;
import com.github.chic.admin.model.param.RefreshTokenParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.CaptchaVO;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.MenuListVO;
import com.github.chic.admin.model.vo.RefreshTokenVO;
import com.github.chic.admin.service.AuthService;
import com.github.chic.admin.service.MenuService;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.model.api.ApiResult;
import com.github.chic.entity.Menu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "Auth 授权")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private AuthService authService;
    @Resource
    private MenuService menuService;

    @ApiOperation("验证码")
    @PostMapping("/captcha")
    public ApiResult<CaptchaVO> captcha() {
        CaptchaVO captchaVO = authService.captcha();
        return ApiResult.success(captchaVO);
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public ApiResult<Object> register(@RequestBody @Valid RegisterParam param) {
        authService.register(param);
        return ApiResult.success();
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public ApiResult<LoginVO> login(@RequestBody @Valid LoginParam param) {
        LoginVO loginVO = authService.login(param);
        return ApiResult.success(loginVO);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public ApiResult<Object> logout(HttpServletRequest request) {
        String token = request.getHeader(JwtProps.header);
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token);
        }
        return ApiResult.success();
    }

    @ApiOperation("刷新 Token")
    @PostMapping("/refreshToken")
    public ApiResult<RefreshTokenVO> refreshToken(@RequestBody @Valid RefreshTokenParam param) {
        RefreshTokenVO vo = authService.refreshToken(param);
        return ApiResult.success(vo);
    }

    @ApiOperation("重设密码")
    @PostMapping("/resetPassword")
    public ApiResult<Object> resetPassword(@RequestBody @Valid PasswordResetParam param) {
        authService.resetPassword(param);
        return ApiResult.success();
    }

    @ApiOperation("菜单权限")
    @GetMapping("/menu/list")
    public ApiResult<List<MenuListVO>> listMenu() {
        List<Menu> menuList = menuService.listByAdminId(SecurityUtils.getCurrentAdminId());
        List<MenuListVO> voList = BeanUtil.copyToList(menuList, MenuListVO.class);
        return ApiResult.success(voList);
    }
}
