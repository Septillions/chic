package com.github.chic.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.chic.common.component.constant.ApiCodeEnum;
import com.github.chic.common.component.constant.RedisKeyAuthEnum;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.model.dto.RedisJwtBufferDTO;
import com.github.chic.common.model.dto.RedisJwtUserDTO;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import com.github.chic.entity.User;
import com.github.chic.portal.component.security.entity.JwtUserDetails;
import com.github.chic.portal.model.param.LoginParam;
import com.github.chic.portal.model.param.RefreshParam;
import com.github.chic.portal.model.param.RegisterParam;
import com.github.chic.portal.model.vo.LoginVO;
import com.github.chic.portal.model.vo.RefreshVO;
import com.github.chic.portal.service.AuthService;
import com.github.chic.portal.service.UserService;
import com.github.chic.portal.util.JwtUtils;
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
    public void register(RegisterParam registerParam) {
        // 检查是否有相同用户名
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getMobile, registerParam.getMobile());
        int count = userService.count(qw);
        if (count > 0) {
            throw new AuthException(1103, "手机号已经注册");
        }
        // 创建用户
        User user = new User();
        user.setUsername(registerParam.getMobile());
        user.setMobile(registerParam.getMobile());
        user.setPassword(passwordEncoder.encode(registerParam.getPassword()));
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }

    @Override
    public LoginVO login(LoginParam loginParam) {
        // 获取用户
        User user = userService.getByMobile(loginParam.getMobile());
        if (user == null) {
            throw new AuthException(1101, "该帐号不存在");
        }
        if (!passwordEncoder.matches(loginParam.getPassword(), user.getPassword())) {
            throw new AuthException(1102, "帐号或密码错误");
        }
        if (user.getStatus() != 1) {
            throw new AuthException(1104, "该帐号已被限制登陆");
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
        String redisAccessTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, accessToken);
        RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisAccessTokenKey);
        if (redisJwtUserDTO != null) {
            redisService.delete(redisAccessTokenKey);
            String refreshToken = redisJwtUserDTO.getRefreshToken();
            String redisRefreshTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_REFRESH_FORMAT.getKey(), mobile, refreshToken);
            redisService.delete(redisRefreshTokenKey);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefreshVO refresh(RefreshParam refreshParam) {
        String oldRefreshToken = refreshParam.getRefreshToken();
        String mobile = JwtUtils.getMobile(oldRefreshToken);
        // 重复刷新请求
        String redisBufferTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_BUFFER_FORMAT.getKey(), mobile, oldRefreshToken);
        RedisJwtBufferDTO redisJwtBufferDTO = (RedisJwtBufferDTO) redisService.get(redisBufferTokenKey);
        if (redisJwtBufferDTO != null) {
            RefreshVO refreshVO = new RefreshVO();
            refreshVO.setAccessToken(redisJwtBufferDTO.getNewAccessToken());
            refreshVO.setRefreshToken(redisJwtBufferDTO.getNewRefreshToken());
            return refreshVO;
        }
        // 校验是否有效
        String redisRefreshTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_REFRESH_FORMAT.getKey(), mobile, oldRefreshToken);
        RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(redisRefreshTokenKey);
        if (redisJwtUserDTO == null) {
            throw new AuthException(ApiCodeEnum.INVALID.getCode(), "RefreshToken 失效");
        }
        // 移除旧 Token
        String oldAccessToken = redisJwtUserDTO.getAccessToken();
        String redisAccessTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, oldAccessToken);
        redisService.delete(redisAccessTokenKey);
        redisService.delete(redisRefreshTokenKey);
        // 生成新 Token
        User user = userService.getByMobile(mobile);
        if (user == null) {
            throw new AuthException(1101, "该帐号不存在");
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
        redisCacheBuffer(mobile, oldAccessToken, oldRefreshToken, newAccessToken, newRefreshToken);
        // VO
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setAccessToken(newAccessToken);
        refreshVO.setRefreshToken(newRefreshToken);
        return refreshVO;
    }

    private void redisCacheToken(String mobile, String accessToken, String refreshToken) {
        String redisAccessTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_ACCESS_FORMAT.getKey(), mobile, accessToken);
        String redisRefreshTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_REFRESH_FORMAT.getKey(), mobile, refreshToken);
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

    private void redisCacheBuffer(String mobile, String oldAccessToken, String oldRefreshToken, String newAccessToken, String newRefreshToken) {
        String redisBufferTokenKey = StrUtil.format(RedisKeyAuthEnum.PORTAL_AUTH_JWT_BUFFER_FORMAT.getKey(), mobile, oldRefreshToken);
        RedisJwtBufferDTO redisJwtBufferDTO = new RedisJwtBufferDTO();
        redisJwtBufferDTO.setOldAccessToken(oldAccessToken);
        redisJwtBufferDTO.setOldRefreshToken(oldRefreshToken);
        redisJwtBufferDTO.setNewAccessToken(newAccessToken);
        redisJwtBufferDTO.setNewRefreshToken(newRefreshToken);
        redisJwtBufferDTO.setRefreshTime(LocalDateTime.now());
        redisService.set(redisBufferTokenKey, redisJwtBufferDTO, JwtProps.bufferTokenExpireTime);
        redisCacheToken(mobile, newAccessToken, newRefreshToken);
    }

    private void redisRemoveToken(String mobile) {
        Set<String> redisAccessTokenKeys = redisService.keys(RedisKeyAuthEnum.PORTAL_AUTH_JWT_ACCESS_PREFIX.getKey() + mobile);
        Set<String> redisRefreshTokenKeys = redisService.keys(RedisKeyAuthEnum.PORTAL_AUTH_JWT_REFRESH_PREFIX.getKey() + mobile);
        redisService.delete(redisAccessTokenKeys);
        redisService.delete(redisRefreshTokenKeys);
    }
}
