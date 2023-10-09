package com.github.chic.admin.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "角色查询")
@Data
public class RoleQuery {
    @ApiModelProperty(value = "状态(0禁用,1启用)")
    private Integer status;
}
