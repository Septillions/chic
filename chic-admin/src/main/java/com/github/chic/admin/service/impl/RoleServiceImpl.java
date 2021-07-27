package com.github.chic.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.admin.component.constant.RedisKeyCacheEnum;
import com.github.chic.admin.mapper.RoleMapper;
import com.github.chic.admin.service.RoleService;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RedisService redisService;

    @Override
    public List<Role> listByAdminId(Long adminId) {
        // Redis Key
        String key = RedisKeyCacheEnum.ADMIN_CACHE_ROLE_PREFIX.getKey() + adminId;
        // 查询 Redis
        List<Role> roleList = (List<Role>) redisService.get(key);
        if (CollUtil.isEmpty(roleList)) {
            // 查询 MySQL
            roleList = this.baseMapper.listByAdminId(adminId);
            // 缓存
            redisService.set(key, roleList, CacheProps.defaultExpireTime);
        }
        return roleList;
    }
}
