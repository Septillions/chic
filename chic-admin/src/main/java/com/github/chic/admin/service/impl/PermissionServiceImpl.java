package com.github.chic.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.component.constant.RedisKeyCacheEnum;
import com.github.chic.admin.mapper.PermissionMapper;
import com.github.chic.admin.service.PermissionService;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.Permission;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private RedisService redisService;

    @Override
    public List<Permission> listByAdminId(Long adminId) {
        // Redis Key
        String key = RedisKeyCacheEnum.ADMIN_CACHE_PERMISSION_PREFIX.getKey() + adminId;
        // 查询 Redis
        List<Permission> permissionList = (List<Permission>) redisService.get(key);
        if (CollUtil.isEmpty(permissionList)) {
            // 查询 MySQL
            permissionList = this.baseMapper.listByAdminId(adminId);
            // 缓存
            redisService.set(key, permissionList, CacheProps.defaultExpireTime);
        }
        return permissionList;
    }
}
