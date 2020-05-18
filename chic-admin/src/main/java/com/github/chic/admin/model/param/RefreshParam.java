package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("刷新参数")
@Data
public class RefreshParam {
    @ApiModelProperty(value = "RefreshToken", required = true)
    @NotBlank(message = "RefreshToken 不能为空")
    private String refreshToken;
}
