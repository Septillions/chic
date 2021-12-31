package com.github.chic.common.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RedisJwtUserDTO {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "accessToken")
    private String accessToken;
    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;
    @ApiModelProperty(value = "IP")
    private String ip;
    @ApiModelProperty(value = "设备系统")
    private String os;
    @ApiModelProperty(value = "设备平台")
    private String platform;
    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;
}
