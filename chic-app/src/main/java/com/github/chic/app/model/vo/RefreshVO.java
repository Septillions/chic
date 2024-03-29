package com.github.chic.app.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RefreshVO {
    @ApiModelProperty(value = "accessToken")
    private String accessToken;
    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;
}
