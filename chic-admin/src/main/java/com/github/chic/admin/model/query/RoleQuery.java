package com.github.chic.admin.model.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleQuery {
    @ApiModelProperty(value = "状态(0禁用,1启用)")
    private Integer status;
}
