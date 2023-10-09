package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(description = "修改管理员参数")
@Data
public class AdminUpdateParam {
    @NotNull
    @ApiModelProperty(value = "管理员ID", required = true)
    private Long id;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "状态(1正常,2禁用)")
    private Integer status;

    @ApiModelProperty(value = "管理员角色ID列表")
    private List<Long> roleIdList;
}
