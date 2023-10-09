package com.github.chic.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaptchaVO {
    @ApiModelProperty(value = "UUID")
    private String uuid;

    @ApiModelProperty(value = "验证码 (Base64)")
    private String image;
}
