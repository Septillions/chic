package com.github.chic.common.component;

import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API 返回格式对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ApiResult<ApiPage<T>> success(Page<T> page) {
        ApiPage<T> data = new ApiPage<>();
        data.setPageIndex(page.getPageNum());
        data.setPageSize(page.getPageSize());
        data.setTotle(page.getTotal());
        data.setItems(page.getResult());
        return new ApiResult<>(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> ApiResult<T> failed() {
        return new ApiResult<>(ApiCodeEnum.FAILED.getCode(), ApiCodeEnum.FAILED.getMsg(), null);
    }

    public static <T> ApiResult<T> failed(String msg) {
        return new ApiResult<>(ApiCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> ApiResult<T> failed(Integer code, String msg) {
        return new ApiResult<>(code, msg, null);
    }
}
