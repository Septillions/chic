package com.github.chic.admin.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "菜单权限查询")
@Data
public class MenuQuery {
    @ApiModelProperty(value = "类型(1显示菜单,2操作权限)")
    private Integer type;

    @ApiModelProperty(value = "状态(0禁用,1启用)")
    private Integer status;
}
