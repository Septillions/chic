package com.github.chic.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.chic.app.component.constant.RedisKeyEnum;
import com.github.chic.app.service.UserService;
import com.github.chic.common.component.props.CacheProps;
import com.github.chic.common.service.RedisService;
import com.github.chic.entity.User;
import com.github.chic.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisService redisService;

    @Override
    public User getByMobile(String mobile) {
        // Redis Key
        String redisKey = StrUtil.format(RedisKeyEnum.APP_CACHE_USER_FORMAT.getKey(), mobile);
        // 查询 Redis
        User user = (User) redisService.get(redisKey);
        if (user == null) {
            // 查询 MySQL
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.lambda().eq(User::getMobile, mobile);
            user = this.baseMapper.selectOne(qw);
            // 缓存
            redisService.set(redisKey, user, CacheProps.defaultExpireTime);
        }
        return user;
    }
}
