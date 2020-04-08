package com.github.chic.common.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JSON返回格式对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult {
    private Integer code;
    private String msg;
    private Object data;

    public static JsonResult success() {
        return new JsonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static JsonResult failed() {
        return new JsonResult(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMsg(), null);
    }

    public static JsonResult failed(String msg) {
        return new JsonResult(ResultCode.FAILED.getCode(), msg, null);
    }

    public static JsonResult failed(Integer code, String msg) {
        return new JsonResult(code, msg, null);
    }
}
