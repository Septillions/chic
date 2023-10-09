package com.github.chic.admin.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(description = "删除管理员参数")
@Data
public class AdminDeleteParam {
    @NotNull(message = "管理员ID不能为空")
    @ApiModelProperty(value = "管理员ID", required = true)
    private Long id;
}
