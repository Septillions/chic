package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "修改角色参数")
@Data
public class RoleUpdateParam {
    @NotNull(message = "角色ID不能为空")
    @ApiModelProperty(value = "角色ID", required = true)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色代码")
    private String code;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "权限代码")
    private String description;

    @ApiModelProperty(value = "系统保留(0否,1是)")
    private Integer isSystem;

    @ApiModelProperty(value = "状态(0禁用,1启用)")
    private Integer status;

    @ApiModelProperty(value = "角色菜单ID列表")
    private List<Long> menuIdList;
}
