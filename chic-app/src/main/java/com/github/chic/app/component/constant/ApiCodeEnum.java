package com.github.chic.app.component.constant;

/**
 * API 返回状态码
 */
public enum ApiCodeEnum {
    // 公共错误
    COMMON_UNAUTHORIZED(1001, "未登录"),
    COMMON_FORBIDDEN(1002, "无权限"),
    COMMON_PARAM_ERROR(1003, "参数错误"),
    COMMON_PARAM_SIGN_ERROR(1004, "参数签名错误"),
    COMMON_PARAM_SERVICE_ERROR(1101, "参数业务异常"),

    // 认证相关
    AUTH_ACCESS_TOKEN_EXPIRED(2001, "AccessToken 已失效"),
    AUTH_REFRESH_TOKEN_EXPIRED(2002, "RefreshToken 已失效"),
    AUTH_MOBILE_NOT_EXIST(2101, "该帐号不存在"),
    AUTH_MOBILE_EXIST(2102, "手机号已经注册"),
    AUTH_PASSWORD_ERROR(2103, "帐号或密码错误"),
    AUTH_STATUS_BAN(2104, "该帐号已被限制登录"),
    AUTH_SMSCODE_ERROR(2105, "短信验证码错误"),
    AUTH_SMSCODE_LIMIT(2106, "短信验证码发送过于频繁，请24小时后再试"),
    AUTH_DEVICE_PARAM_ERROR(2201, "设备参数错误"),

    // 第三方服务
    ESCROW_ALIYUN_UVERIFY_ERROR(3001, "一键登录获取手机号失败"),
    ESCROW_ALIYUN_SMS_ERROR(3101, "阿里云短信发送失败"),
    ESCROW_ALIPAY_APPPAY_ERROR(3201, "发起支付宝支付失败"),
    ESCROW_WXPAY_APPPAY_ERROR(3301, "发起微信支付失败"),
    ;
    private final Integer code;
    private final String msg;

    ApiCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
