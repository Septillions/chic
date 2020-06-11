package com.github.chic.admin.component.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.common.util.ServletUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 统一日志处理切面
 */
@Aspect
@Component
public class WebLogAspect {
    /**
     * LOGGER
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Around("execution(public * com.github.chic.admin.controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 请求
        HttpServletRequest request = ServletUtils.getRequest();
        // 日志
        StringBuilder log = new StringBuilder();
        // 记录请求内容
        log.append(request.getMethod()).append(" : ").append(request.getRequestURL()).append(" - ");
        log.append("ARGS : ").append(Arrays.toString(joinPoint.getArgs())).append(" - ");
        log.append("UID : ").append(getCurrentAdminId()).append(" - ");
        log.append("IP : ").append(ServletUtil.getClientIP(request)).append(" - ");
        try {
            // 执行请求方法
            Object result = joinPoint.proceed(joinPoint.getArgs());
            log.append("SPEND TIME : ").append(System.currentTimeMillis() - startTime);
            LOGGER.info(log.toString());
            return result;
        } catch (Throwable e) {
            log.append("SPEND TIME : ").append(System.currentTimeMillis() - startTime);
            LOGGER.info(log.toString());
            throw e;
        }
    }

    /**
     * 获取当前用户 ID
     */
    private Integer getCurrentAdminId() {
        try {
            return SecurityUtils.getCurrentAdminId();
        } catch (Exception e) {
            return null;
        }
    }
}
