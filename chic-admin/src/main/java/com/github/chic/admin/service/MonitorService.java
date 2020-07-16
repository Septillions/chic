package com.github.chic.admin.service;

import com.github.chic.common.model.dto.RedisJwtAdminDTO;
import com.github.chic.common.model.dto.RedisJwtUserDTO;

import java.util.List;

public interface MonitorService {
    List<RedisJwtAdminDTO> listOnlineAdmin();

    List<RedisJwtUserDTO> listOnlineUser();
}
