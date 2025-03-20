package com.github.chic.admin.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminQuery {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "状态(1正常,2禁用)")
    private Integer status;
}
