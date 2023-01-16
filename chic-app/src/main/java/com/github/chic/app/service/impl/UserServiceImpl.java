package com.github.chic.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.common.config.CacheProps;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.User;
import com.github.chic.app.component.constant.RedisKeyCacheEnum;
import com.github.chic.app.mapper.UserMapper;
import com.github.chic.app.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisService redisService;

    @Override
    public User getByMobile(String mobile) {
        // Redis Key
        String key = RedisKeyCacheEnum.APP_CACHE_USER_PREFIX.getKey() + mobile;
        // 查询 Redis
        User user = (User) redisService.get(key);
        if (user == null) {
            // 查询 MySQL
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.lambda().eq(User::getMobile, mobile);
            user = this.baseMapper.selectOne(qw);
            // 缓存
            redisService.set(key, user, CacheProps.defaultExpireTime);
        }
        return user;
    }
}
