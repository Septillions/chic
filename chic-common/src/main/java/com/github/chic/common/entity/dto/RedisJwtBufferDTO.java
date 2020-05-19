package com.github.chic.common.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisJwtBufferDTO {
    private String oldAccessToken;
    private String oldRefreshToken;
    private String newAccessToken;
    private String newRefreshToken;
    private LocalDateTime refreshTime;
}
