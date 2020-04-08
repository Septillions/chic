package com.github.chic.admin.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisJwtDTO {
    private String username;
    private String jwt;
    private String ip;
    private String os;
    private String platform;
    private LocalDateTime loginTime;
}
