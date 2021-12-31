package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "登录参数")
@Data
public class LoginParam {
    @ApiModelProperty(value = "用户名", required = true, example = "admin")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "密码", required = true, example = "admin")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty(value = "UUID", required = true)
    @NotBlank(message = "UUID不能为空")
    private String uuid;
    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
