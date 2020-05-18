package com.github.chic.common.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisJwtAdminDTO {
    private String username;
    private String accessToken;
    private String refreshToken;
    private String ip;
    private String os;
    private String platform;
    private LocalDateTime loginTime;
}
