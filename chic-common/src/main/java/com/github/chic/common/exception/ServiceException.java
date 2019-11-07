package com.github.chic.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义 业务相关异常
 * errCode 3xxx 格式
 */
@Getter
@Setter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    private Integer errCode;
    private String errMsg;
}
