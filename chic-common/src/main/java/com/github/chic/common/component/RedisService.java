package com.github.chic.common.component;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 业务类
 */
@Component
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public void set(String key, Object value, Long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void expire(String key, Long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    public void expire(String key, Long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }
}