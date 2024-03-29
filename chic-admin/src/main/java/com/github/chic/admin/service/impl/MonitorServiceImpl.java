package com.github.chic.admin.service.impl;

import com.github.chic.admin.service.MonitorService;
import com.github.chic.common.component.constant.BaseRedisKeyEnum;
import com.github.chic.common.model.dto.RedisJwtAdminDTO;
import com.github.chic.common.model.dto.RedisJwtUserDTO;
import com.github.chic.common.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MonitorServiceImpl implements MonitorService {
    @Resource
    private RedisService redisService;

    @Override
    public List<RedisJwtAdminDTO> listOnlineAdmin() {
        Set<String> keys = redisService.keys(BaseRedisKeyEnum.ADMIN_AUTH_JWT_ACCESS_PREFIX.getKey() + "*");
        List<RedisJwtAdminDTO> redisJwtAdminDTOList = new ArrayList<>();
        for (String key : keys) {
            RedisJwtAdminDTO redisJwtAdminDTO = (RedisJwtAdminDTO) redisService.get(key);
            if (redisJwtAdminDTO != null) {
                redisJwtAdminDTOList.add(redisJwtAdminDTO);
            }
        }
        return redisJwtAdminDTOList;
    }

    @Override
    public List<RedisJwtUserDTO> listOnlineUser() {
        Set<String> keys = redisService.keys(BaseRedisKeyEnum.APP_AUTH_JWT_ACCESS_PREFIX.getKey() + "*");
        List<RedisJwtUserDTO> redisJwtUserDTOList = new ArrayList<>();
        for (String key : keys) {
            RedisJwtUserDTO redisJwtUserDTO = (RedisJwtUserDTO) redisService.get(key);
            if (redisJwtUserDTO != null) {
                redisJwtUserDTOList.add(redisJwtUserDTO);
            }
        }
        return redisJwtUserDTOList;
    }
}
