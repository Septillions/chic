package com.github.chic.admin.service.impl;

import com.github.chic.admin.model.constant.RedisKeyEnum;
import com.github.chic.admin.model.dto.RedisJwtDTO;
import com.github.chic.admin.service.MonitorService;
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
    public List<RedisJwtDTO> listOnlineAdmin() {
        Set<String> keys = redisService.keys(RedisKeyEnum.AUTH_JWT_PREFIX.getKey() + "*");
        List<RedisJwtDTO> redisJwtDTOList = new ArrayList<>();
        for (String key : keys) {
            RedisJwtDTO redisJwtDTO = (RedisJwtDTO) redisService.get(key);
            if (redisJwtDTO != null) {
                redisJwtDTOList.add(redisJwtDTO);
            }
        }
        return redisJwtDTOList;
    }
}
