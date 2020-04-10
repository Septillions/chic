package com.github.chic.common.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    Object get(String key);

    void set(String key, Object value);

    void set(String key, Object value, Long timeout);

    void set(String key, Object value, Long timeout, TimeUnit unit);

    Set<String> keys(String pattern);

    void expire(String key, Long timeout);

    void expire(String key, Long timeout, TimeUnit unit);
}
