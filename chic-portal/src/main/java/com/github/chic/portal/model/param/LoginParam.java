package com.github.chic.portal.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel("登录参数")
@Data
public class LoginParam {
    @ApiModelProperty(value = "手机号", required = true, example = "13000000000")
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @ApiModelProperty(value = "密码", required = true, example = "13000000000")
    @NotBlank(message = "密码不能为空")
    private String password;
}
