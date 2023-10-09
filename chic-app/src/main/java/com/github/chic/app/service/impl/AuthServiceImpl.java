package com.github.chic.app.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.chic.app.component.constant.ApiCodeEnum;
import com.github.chic.app.component.exception.ApiException;
import com.github.chic.app.component.security.entity.JwtUserDetails;
import com.github.chic.app.model.param.LoginParam;
import com.github.chic.app.model.param.RefreshParam;
import com.github.chic.app.model.param.RegisterParam;
import com.github.chic.app.model.vo.LoginVO;
import com.github.chic.app.model.vo.RefreshVO;
import com.github.chic.app.service.AuthService;
import com.github.chic.app.service.UserService;
import com.github.chic.app.util.JwtUtils;
import com.github.chic.common.component.constant.BaseApiCodeEnum;
import com.github.chic.common.component.constant.BaseRedisKeyEnum;
import com.github.chic.common.component.exception.BaseException;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.model.dto.RedisJwtUserDTO;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import com.github.chic.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private UserService userService;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisService redisService;

    @Override
    public void register(RegisterParam param) {
        // 检查是否有相同用户名
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getMobile, param.getMobile());
        int count = userService.count(qw);
        if (count > 0) {
            throw new ApiException(ApiCodeEnum.AUTH_MOBILE_EXIST);
        }
        // 创建用户
        User user = new User();
        user.setUsername(param.getMobile());
        user.setMobile(param.getMobile());
        user.setPassword(passwordEncoder.encode(param.getPassword()));
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }

    @Override
    public LoginVO login(LoginParam param) {
        // 获取用户
        User user = userService.getByMobile(param.getMobile());
        if (user == null) {
            // 手机号不存在
            throw new ApiException(ApiCodeEnum.AUTH_MOBILE_NOT_EXIST);
        }
        if (!passwordEncoder.matches(param.getPassword(), user.getPassword())) {
            // 密码错误
            throw new ApiException(ApiCodeEnum.AUTH_PASSWORD_ERROR);
        }
        if (user.getStatus() != 1) {
            // 账号已被禁用
            throw new ApiException(ApiCodeEnum.AUTH_STATUS_ERROR);
        }
        // 删除已登录Token 保证Token唯一
        redisRemoveToken(user.getMobile());
        // Security
        JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(user.getMobile());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        String accessToken = JwtUtils.generateAccessToken(jwtUserDetails);
        String refreshToken = JwtUtils.generateRefreshToken(jwtUserDetails);
        // Redis
        redisCacheToken(user.getMobile(), accessToken, refreshToken);
        // VO
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        return loginVO;
    }

    @Override
    public void logout(String accessToken) {
        String mobile = JwtUtils.getMobile(accessToken);
        String redisAccessTokenKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, accessToken);
        RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisAccessTokenKey);
        if (redisJwtUserDTO != null) {
            redisService.delete(redisAccessTokenKey);
            String refreshToken = redisJwtUserDTO.getRefreshToken();
            String redisRefreshTokenKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_REFRESH_FORMAT.getKey(), mobile, refreshToken);
            redisService.delete(redisRefreshTokenKey);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefreshVO refresh(RefreshParam param) {
        String oldRefreshToken = param.getRefreshToken();
        String mobile = JwtUtils.getMobile(oldRefreshToken);
        // 校验是否有效
        String redisRefreshTokenKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_REFRESH_FORMAT.getKey(), mobile, oldRefreshToken);
        RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisRefreshTokenKey);
        if (redisJwtUserDTO == null) {
            throw new BaseException(BaseApiCodeEnum.TOKEN_EXPIRED);
        }
        // 移除旧 Token
        String oldAccessToken = redisJwtUserDTO.getAccessToken();
        String redisAccessTokenKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, oldAccessToken);
        redisService.delete(redisAccessTokenKey);
        redisService.delete(redisRefreshTokenKey);
        // 生成新 Token
        User user = userService.getByMobile(mobile);
        if (user == null) {
            throw new ApiException(ApiCodeEnum.AUTH_MOBILE_NOT_EXIST);
        }
        // Security
        JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(mobile);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        String newAccessToken = JwtUtils.generateAccessToken(jwtUserDetails);
        String newRefreshToken = JwtUtils.generateRefreshToken(jwtUserDetails);
        // Redis
        redisCacheToken(mobile, newAccessToken, newRefreshToken);
        // VO
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setAccessToken(newAccessToken);
        refreshVO.setRefreshToken(newRefreshToken);
        return refreshVO;
    }

    private void redisCacheToken(String mobile, String accessToken, String refreshToken) {
        String redisAccessTokenKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, accessToken);
        String redisRefreshTokenKey = StrUtil.format(BaseRedisKeyEnum.APP_AUTH_JWT_REFRESH_FORMAT.getKey(), mobile, refreshToken);
        UserAgent ua = ServletUtils.getUserAgent();
        RedisJwtUserDTO redisJwtUserDTO = new RedisJwtUserDTO();
        redisJwtUserDTO.setMobile(mobile);
        redisJwtUserDTO.setAccessToken(accessToken);
        redisJwtUserDTO.setRefreshToken(refreshToken);
        redisJwtUserDTO.setOs(ua.getOs().toString());
        redisJwtUserDTO.setPlatform(ua.getPlatform().toString());
        redisJwtUserDTO.setIp(ServletUtils.getIpAddress());
        redisJwtUserDTO.setLoginTime(LocalDateTime.now());
        redisService.set(redisAccessTokenKey, redisJwtUserDTO, JwtProps.accessTokenExpireTime);
        redisService.set(redisRefreshTokenKey, redisJwtUserDTO, JwtProps.refreshTokenExpireTime);
    }

    private void redisRemoveToken(String mobile) {
        Set<String> redisAccessTokenKeys = redisService.keys(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_PREFIX.getKey() + mobile);
        Set<String> redisRefreshTokenKeys = redisService.keys(BaseRedisKeyEnum.APP_AUTH_JWT_REFRESH_PREFIX.getKey() + mobile);
        redisService.delete(redisAccessTokenKeys);
        redisService.delete(redisRefreshTokenKeys);
    }
}
