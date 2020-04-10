package com.github.chic.admin.service;

import com.github.chic.admin.model.dto.RedisJwtDTO;

import java.util.List;

public interface MonitorService {
    List<RedisJwtDTO> listOnlineAdmin();
}
