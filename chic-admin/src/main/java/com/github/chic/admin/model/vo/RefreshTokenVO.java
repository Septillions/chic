package com.github.chic.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RefreshTokenVO {
    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "accessToken 过期时间")
    private Date accessExpire;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;

    @ApiModelProperty(value = "refreshToken 过期时间")
    private Date refreshExpire;
}
