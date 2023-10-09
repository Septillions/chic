package com.github.chic.common.model.api;

import com.github.chic.common.component.constant.BaseApiCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * API 返回格式对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    @ApiModelProperty(value = "状态码 (0成功,!=0失败)", position = 1)
    private Integer code;
    @ApiModelProperty(value = "状态码描述", position = 2)
    private String msg;
    @ApiModelProperty(value = "结果数据", position = 3)
    private T data;

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(BaseApiCodeEnum.SUCCESS.getCode(), BaseApiCodeEnum.SUCCESS.getMsg(), null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(BaseApiCodeEnum.SUCCESS.getCode(), BaseApiCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ApiResult<ApiPage<T>> page(List<T> list) {
        ApiPage<T> apiPage = new ApiPage<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        apiPage.setPageIndex(pageInfo.getPageNum());
        apiPage.setPageSize(pageInfo.getPageSize());
        apiPage.setPages(pageInfo.getPages());
        apiPage.setTotal(pageInfo.getTotal());
        apiPage.setItems(pageInfo.getList());
        return new ApiResult<>(BaseApiCodeEnum.SUCCESS.getCode(), BaseApiCodeEnum.SUCCESS.getMsg(), apiPage);
    }

    public static <T> ApiResult<T> failed() {
        return new ApiResult<>(BaseApiCodeEnum.FAILED.getCode(), BaseApiCodeEnum.FAILED.getMsg(), null);
    }

    public static <T> ApiResult<T> failed(String msg) {
        return new ApiResult<>(BaseApiCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> ApiResult<T> failed(Integer code, String msg) {
        return new ApiResult<>(code, msg, null);
    }
}
