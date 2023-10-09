package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "刷新 Token 参数")
@Data
public class RefreshTokenParam {
    @NotBlank(message = "RefreshToken 不能为空")
    @ApiModelProperty(value = "RefreshToken", required = true)
    private String refreshToken;
}
