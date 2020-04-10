package com.github.chic.portal.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisJwtDTO {
    private String mobile;
    private String jwt;
    private String ip;
    private String os;
    private String platform;
    private LocalDateTime loginTime;
}
