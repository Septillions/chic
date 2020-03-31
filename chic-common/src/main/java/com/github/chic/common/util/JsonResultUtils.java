package com.github.chic.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.chic.common.component.JsonResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JSON 格式返回数据工具类
 */
public class JsonResultUtils {

    public static void responseJson(HttpServletResponse response, Integer code, String msg) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        JsonResult jsonResult = JsonResult.failed(code, msg);
        response.getWriter().println(JSON.toJSONString(jsonResult, SerializerFeature.WriteMapNullValue));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
