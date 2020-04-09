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
public class JsonResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> JsonResult<T> success() {
        return new JsonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> JsonResult<PageResult<T>> success(Page<T> page) {
        PageResult<T> data = new PageResult<>();
        data.setPageIndex(page.getPageNum());
        data.setPageSize(page.getPageSize());
        data.setTotle(page.getTotal());
        data.setItems(page.getResult());
        return new JsonResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> JsonResult<T> failed() {
        return new JsonResult<>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), null);
    }

    public static <T> JsonResult<T> failed(String msg) {
        return new JsonResult<>(ResultCode.FAILED.getCode(), msg, null);
    }

    public static <T> JsonResult<T> failed(Integer code, String msg) {
        return new JsonResult<>(code, msg, null);
    }
}
