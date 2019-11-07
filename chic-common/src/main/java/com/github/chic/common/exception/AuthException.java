package com.github.chic.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义 认证相关异常
 * errCode 1xxx 格式
 */
@Getter
@Setter
@AllArgsConstructor
public class AuthException extends RuntimeException {
    private Integer errCode;
    private String errMsg;
}
