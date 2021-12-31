package com.github.chic.portal.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginVO {
    @ApiModelProperty(value = "accessToken")
    private String accessToken;
    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;
}
