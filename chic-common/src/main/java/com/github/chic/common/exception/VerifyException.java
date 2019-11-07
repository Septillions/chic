package com.github.chic.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义 校验相关异常
 * errCode 2xxx 格式
 */
@Getter
@Setter
@AllArgsConstructor
public class VerifyException extends RuntimeException {
    private Integer errCode;
    private String errMsg;
}
