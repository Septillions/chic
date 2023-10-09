package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "添加管理员参数")
@Data
public class AdminAddParam {
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotNull(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态(0禁用,1启用)", required = true)
    private Integer status;

    @ApiModelProperty(value = "管理员角色ID列表")
    private List<Long> roleIdList;
}
