package com.github.chic.admin.service;

import com.github.chic.common.entity.dto.RedisJwtAdminDTO;

import java.util.List;

public interface MonitorService {
    List<RedisJwtAdminDTO> listOnlineAdmin();
}
