package com.github.chic.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.mapper.AdminMapper;
import com.github.chic.admin.mapper.PermissionMapper;
import com.github.chic.admin.mapper.RoleMapper;
import com.github.chic.admin.model.param.LoginParam;
import com.github.chic.admin.model.param.RefreshParam;
import com.github.chic.admin.model.param.RegisterParam;
import com.github.chic.admin.model.vo.LoginVO;
import com.github.chic.admin.model.vo.RefreshVO;
import com.github.chic.admin.security.entity.JwtAdminDetails;
import com.github.chic.admin.service.AdminService;
import com.github.chic.admin.util.JwtUtils;
import com.github.chic.common.component.constant.ApiCodeEnum;
import com.github.chic.common.component.constant.RedisKeyEnum;
import com.github.chic.common.config.JwtProps;
import com.github.chic.common.exception.AuthException;
import com.github.chic.common.model.dto.RedisJwtAdminDTO;
import com.github.chic.common.model.dto.RedisJwtBufferDTO;
import com.github.chic.common.service.RedisService;
import com.github.chic.common.util.ServletUtils;
import com.github.chic.entity.Admin;
import com.github.chic.entity.Permission;
import com.github.chic.entity.Role;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionMapper permissionMapper;
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private RedisService redisService;

    @Override
    public void register(RegisterParam registerParam) {
        // 检查是否有相同用户名
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername, registerParam.getUsername());
        Integer count = adminMapper.selectCount(wrapper);
        if (count > 0) {
            throw new AuthException(1103, "用户名已经注册");
        }
        // 创建用户
        Admin admin = new Admin();
        admin.setUsername(registerParam.getUsername());
        admin.setPassword(passwordEncoder.encode(registerParam.getPassword()));
        admin.setCreateTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insert(admin);
    }

    @Override
    public LoginVO login(LoginParam loginParam) {
        // 获取用户
        Admin admin = getByUsername(loginParam.getUsername());
        if (admin == null) {
            throw new AuthException(1101, "该帐号不存在");
        }
        if (!passwordEncoder.matches(loginParam.getPassword(), admin.getPassword())) {
            throw new AuthException(1102, "帐号或密码错误");
        }
        // Security
        JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(loginParam.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, null, jwtAdminDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        String accessToken = JwtUtils.generateAccessToken(jwtAdminDetails);
        String refreshToken = JwtUtils.generateRefreshToken(jwtAdminDetails);
        // Redis
        redisCacheToken(admin.getUsername(), accessToken, refreshToken);
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        return loginVO;
    }

    @Override
    public void logout(String accessToken) {
        String username = JwtUtils.getUsername(accessToken);
        String redisAccessTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_ACCESS_FORMAT.getKey(), username, accessToken);
        RedisJwtAdminDTO redisJwtAdminDTO = (RedisJwtAdminDTO) redisService.get(redisAccessTokenKey);
        if (redisJwtAdminDTO != null) {
            redisService.delete(redisAccessTokenKey);
            String refreshToken = redisJwtAdminDTO.getRefreshToken();
            String redisRefreshTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_REFRESH_FORMAT.getKey(), username, refreshToken);
            redisService.delete(redisRefreshTokenKey);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefreshVO refresh(RefreshParam refreshParam) {
        String oldRefreshToken = refreshParam.getRefreshToken();
        String username = JwtUtils.getUsername(oldRefreshToken);
        // 重复刷新请求
        String redisBufferTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_BUFFER_FORMAT.getKey(), username, oldRefreshToken);
        RedisJwtBufferDTO redisJwtBufferDTO = (RedisJwtBufferDTO) redisService.get(redisBufferTokenKey);
        if (redisJwtBufferDTO != null) {
            RefreshVO refreshVO = new RefreshVO();
            refreshVO.setAccessToken(redisJwtBufferDTO.getNewAccessToken());
            refreshVO.setRefreshToken(redisJwtBufferDTO.getNewRefreshToken());
            return refreshVO;
        }
        // 校验是否有效
        String redisRefreshTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_REFRESH_FORMAT.getKey(), username, oldRefreshToken);
        RedisJwtAdminDTO redisJwtAdminDTO = (RedisJwtAdminDTO) redisService.get(redisRefreshTokenKey);
        if (redisJwtAdminDTO == null) {
            throw new AuthException(ApiCodeEnum.INVALID.getCode(), "RefreshToken 失效");
        }
        // 移除旧 Token
        String oldAccessToken = redisJwtAdminDTO.getAccessToken();
        String redisAccessTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_ACCESS_FORMAT.getKey(), username, oldAccessToken);
        redisService.delete(redisAccessTokenKey);
        redisService.delete(redisRefreshTokenKey);
        // 生成新 Token
        Admin admin = getByUsername(username);
        if (admin == null) {
            throw new AuthException(1101, "该帐号不存在");
        }
        // Security
        JwtAdminDetails jwtAdminDetails = (JwtAdminDetails) userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtAdminDetails, null, jwtAdminDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT
        String newAccessToken = JwtUtils.generateAccessToken(jwtAdminDetails);
        String newRefreshToken = JwtUtils.generateRefreshToken(jwtAdminDetails);
        // Redis
        redisCacheToken(username, newAccessToken, newRefreshToken);
        redisCacheBuffer(username, oldAccessToken, oldRefreshToken, newAccessToken, newRefreshToken);
        // VO
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setAccessToken(newAccessToken);
        refreshVO.setRefreshToken(newRefreshToken);
        return refreshVO;
    }

    @Override
    public Admin getByUsername(String username) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Admin::getUsername, username);
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public List<Role> listRoleByAdminId(Integer adminId) {
        return roleMapper.selectRoleListByAdminId(adminId);
    }

    @Override
    public List<Permission> listPermissionByAdminId(Integer adminId) {
        return permissionMapper.selectPermissionListByAdminId(adminId);
    }

    private void redisCacheToken(String username, String accessToken, String refreshToken) {
        String redisAccessTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_ACCESS_FORMAT.getKey(), username, accessToken);
        String redisRefreshTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_REFRESH_FORMAT.getKey(), username, refreshToken);
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

    private void redisCacheBuffer(String username, String oldAccessToken, String oldRefreshToken, String newAccessToken, String newRefreshToken) {
        String redisBufferTokenKey = StrUtil.format(RedisKeyEnum.AUTH_ADMIN_JWT_BUFFER_FORMAT.getKey(), username, oldRefreshToken);
        RedisJwtBufferDTO redisJwtBufferDTO = new RedisJwtBufferDTO();
        redisJwtBufferDTO.setOldAccessToken(oldAccessToken);
        redisJwtBufferDTO.setOldRefreshToken(oldRefreshToken);
        redisJwtBufferDTO.setNewAccessToken(newAccessToken);
        redisJwtBufferDTO.setNewRefreshToken(newRefreshToken);
        redisJwtBufferDTO.setRefreshTime(LocalDateTime.now());
        redisService.set(redisBufferTokenKey, redisJwtBufferDTO, JwtProps.bufferTokenExpireTime);
        redisCacheToken(username, newAccessToken, newRefreshToken);
    }
}
