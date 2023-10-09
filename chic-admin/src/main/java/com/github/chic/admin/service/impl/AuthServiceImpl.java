package com.github.chic.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.chic.admin.component.constant.ApiCodeEnum;
import com.github.chic.admin.component.constant.RedisKeyEnum;
import com.github.chic.admin.component.exception.ApiException;
import com.github.chic.admin.component.security.entity.JwtAdminDetails;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.PasswordResetParam;
import com.github.chic.admin.model.param.RefreshTokenParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.CaptchaVO;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.RefreshTokenVO;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.service.AuthService;
import com.github.chic.admin.util.JwtUtils;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.common.component.constant.BaseApiCodeEnum;
import com.github.chic.common.component.constant.BaseRedisKeyEnum;
import com.github.chic.common.component.exception.BaseException;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.model.dto.RedisJwtAdminDTO;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import com.github.chic.entity.Admin;
import com.wf.captcha.SpecCaptcha;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AdminService adminService;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisService redisService;

    @Override
    public CaptchaVO captcha() {
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        String code = captcha.text().toLowerCase();
        String uuid = IdUtil.fastSimpleUUID();
        String key = BaseRedisKeyEnum.ADMIN_AUTH_CAPTCHA_PREFIX.getKey() + uuid;
        redisService.set(key, code, 300L);
        CaptchaVO vo = new CaptchaVO();
        vo.setUuid(uuid);
        vo.setImage(captcha.toBase64());
        return vo;
    }

    @Override
    public void register(RegisterParam param) {
        // 检查是否有相同用户名
        QueryWrapper<Admin> qw = new QueryWrapper<>();
        qw.lambda().eq(Admin::getUsername, param.getUsername());
        int count = adminService.count(qw);
        if (count > 0) {
            throw new ApiException(ApiCodeEnum.AUTH_USERNAME_EXIST);
        }
        // 创建用户
        Admin admin = new Admin();
        admin.setUsername(param.getUsername());
        admin.setPassword(passwordEncoder.encode(param.getPassword()));
        admin.setStatus(1);
        admin.setNickname(param.getUsername());
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminService.save(admin);
    }

    @Override
    public LoginVO login(LoginParam param) {
        // 校验验证码
        String captchaKey = BaseRedisKeyEnum.ADMIN_AUTH_CAPTCHA_PREFIX.getKey() + param.getUuid();
        String captcha = (String) redisService.get(captchaKey);
        if (!StrUtil.equalsIgnoreCase(captcha, param.getCaptcha().trim())) {
            // 验证码不正确
            throw new ApiException(ApiCodeEnum.AUTH_CAPTCHA_ERROR);
        }
        // 获取用户
        Admin admin = adminService.getByUsername(param.getUsername());
        if (admin == null) {
            // 用户名不存在
            throw new ApiException(ApiCodeEnum.AUTH_USERNAME_NOT_EXIST);
        }
        if (!passwordEncoder.matches(param.getPassword(), admin.getPassword())) {
            // 密码错误
            throw new ApiException(ApiCodeEnum.AUTH_PASSWORD_ERROR);
        }
        if (admin.getStatus() != 1) {
            // 账号已被禁用
            throw new ApiException(ApiCodeEnum.AUTH_STATUS_ERROR);
        }
        // Security
        JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(param.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, null, jwtAdminDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        Date accessExpire = new Date(System.currentTimeMillis() + JwtProps.accessTokenExpireTime * 1000);
        String accessToken = JwtUtils.generateAccessToken(jwtAdminDetails, accessExpire);
        Date refreshExpire = new Date(System.currentTimeMillis() + JwtProps.refreshTokenExpireTime * 1000);
        String refreshToken = JwtUtils.generateRefreshToken(jwtAdminDetails, refreshExpire);
        // Redis
        redisCacheToken(admin.getUsername(), accessToken, refreshToken);
        // AdminVO
        LoginVO.Admin adminVO = new LoginVO.Admin();
        adminVO.setId(admin.getId());
        adminVO.setUsername(admin.getUsername());
        adminVO.setNickname(admin.getNickname());
        adminVO.setAvatarUrl(admin.getAvatarUrl());
        // TokenVO
        LoginVO.Token tokenVO = new LoginVO.Token();
        tokenVO.setAccessExpire(accessExpire);
        tokenVO.setAccessToken(accessToken);
        tokenVO.setRefreshExpire(refreshExpire);
        tokenVO.setRefreshToken(refreshToken);
        // VO
        LoginVO loginVO = new LoginVO();
        loginVO.setAdmin(adminVO);
        loginVO.setToken(tokenVO);
        return loginVO;
    }

    @Override
    public void logout(String accessToken) {
        Long adminId = JwtUtils.getAdminId(accessToken);
        String username = JwtUtils.getUsername(accessToken);
        String redisAccessTokenKey = StrUtil.format(BaseRedisKeyEnum.ADMIN_AUTH_JWT_ACCESS_FORMAT.getKey(), username, accessToken);
        RedisJwtAdminDTO redisJwtAdminDTO = (RedisJwtAdminDTO) redisService.get(redisAccessTokenKey);
        if (redisJwtAdminDTO != null) {
            // 删除 AccessToken 缓存
            redisService.delete(redisAccessTokenKey);
            // 删除 RefreshToken 缓存
            String refreshToken = redisJwtAdminDTO.getRefreshToken();
            String redisRefreshTokenKey = StrUtil.format(BaseRedisKeyEnum.ADMIN_AUTH_JWT_REFRESH_FORMAT.getKey(), username, refreshToken);
            redisService.delete(redisRefreshTokenKey);
            // 删除 Role 缓存
            String redisRoleKey = RedisKeyEnum.ADMIN_CACHE_ROLE_PREFIX.getKey() + adminId;
            redisService.delete(redisRoleKey);
            // 删除 Permission 缓存
            String redisPermissionKey = RedisKeyEnum.ADMIN_CACHE_MENU_PREFIX.getKey() + adminId;
            redisService.delete(redisPermissionKey);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefreshTokenVO refreshToken(RefreshTokenParam param) {
        String oldRefreshToken = param.getRefreshToken();
        String username = JwtUtils.getUsername(oldRefreshToken);
        // 校验是否有效
        String redisRefreshTokenKey = StrUtil.format(BaseRedisKeyEnum.ADMIN_AUTH_JWT_REFRESH_FORMAT.getKey(), username, oldRefreshToken);
        RedisJwtAdminDTO redisJwtAdminDTO = (RedisJwtAdminDTO) redisService.get(redisRefreshTokenKey);
        if (redisJwtAdminDTO == null) {
            throw new BaseException(BaseApiCodeEnum.TOKEN_EXPIRED);
        }
        // 移除旧 Token
        String oldAccessToken = redisJwtAdminDTO.getAccessToken();
        String redisAccessTokenKey = StrUtil.format(BaseRedisKeyEnum.ADMIN_AUTH_JWT_ACCESS_FORMAT.getKey(), username, oldAccessToken);
        redisService.delete(redisAccessTokenKey);
        redisService.delete(redisRefreshTokenKey);
        // 生成新 Token
        Admin admin = adminService.getByUsername(username);
        if (admin == null) {
            throw new ApiException(ApiCodeEnum.AUTH_USERNAME_NOT_EXIST);
        }
        // Security
        JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, null, jwtAdminDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        Date newAccessExpire = new Date(System.currentTimeMillis() + JwtProps.accessTokenExpireTime * 1000);
        Date newRefreshExpire = new Date(System.currentTimeMillis() + JwtProps.refreshTokenExpireTime * 1000);
        String newAccessToken = JwtUtils.generateAccessToken(jwtAdminDetails, newAccessExpire);
        String newRefreshToken = JwtUtils.generateRefreshToken(jwtAdminDetails, newRefreshExpire);
        // Redis
        redisCacheToken(username, newAccessToken, newRefreshToken);
        // VO
        RefreshTokenVO refreshTokenVO = new RefreshTokenVO();
        refreshTokenVO.setAccessToken(newAccessToken);
        refreshTokenVO.setAccessExpire(newAccessExpire);
        refreshTokenVO.setRefreshToken(newRefreshToken);
        refreshTokenVO.setRefreshExpire(newRefreshExpire);
        return refreshTokenVO;
    }

    @Override
    public void resetPassword(PasswordResetParam param) {
        Admin admin = adminService.getById(SecurityUtils.getCurrentAdminId());
        if (!passwordEncoder.matches(param.getPasswordOld(), admin.getPassword())) {
            // 原账号密码错误
            throw new ApiException(ApiCodeEnum.AUTH_RESET_PASSWORD_ERROR);
        }
        admin.setPassword(passwordEncoder.encode(param.getPassword()));
        adminService.updateById(admin);
    }

    private void redisCacheToken(String username, String accessToken, String refreshToken) {
        String redisAccessTokenKey = StrUtil.format(BaseRedisKeyEnum.ADMIN_AUTH_JWT_ACCESS_FORMAT.getKey(), username, accessToken);
        String redisRefreshTokenKey = StrUtil.format(BaseRedisKeyEnum.ADMIN_AUTH_JWT_REFRESH_FORMAT.getKey(), username, refreshToken);
        UserAgent ua = ServletUtils.getUserAgent();
        RedisJwtAdminDTO redisJwtAdminDTO = new RedisJwtAdminDTO();
        redisJwtAdminDTO.setUsername(username);
        redisJwtAdminDTO.setAccessToken(accessToken);
        redisJwtAdminDTO.setRefreshToken(refreshToken);
        redisJwtAdminDTO.setOs(ua.getOs().toString());
        redisJwtAdminDTO.setPlatform(ua.getPlatform().toString());
        redisJwtAdminDTO.setIp(ServletUtils.getIpAddress());
        redisJwtAdminDTO.setLoginTime(LocalDateTime.now());
        redisService.set(redisAccessTokenKey, redisJwtAdminDTO, JwtProps.accessTokenExpireTime);
        redisService.set(redisRefreshTokenKey, redisJwtAdminDTO, JwtProps.refreshTokenExpireTime);
    }
}
