package com.github.chic.admin.component.aspect;

import cn.hutool.extra.servlet.ServletUtil;
import com.github.chic.admin.util.SecurityUtils;
import com.github.chic.common.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 统一日志处理切面
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {
    /**
     * LOG TEMPLATE
     * {METHOD} - {URL} - ARGS : {} - UID : {} - IP : {} - SPEND TIME : {}
     */
    private static final String LOG_TEMPLATE = "{} : {} - ARGS : {} - UID : {} - IP : {} - SPEND TIME : {}";

    @Around("execution(public * com.github.chic.admin.controller..*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        // 请求
        HttpServletRequest request = ServletUtils.getRequest();
        // 记录请求内容
        String method = request.getMethod();
        String url = request.getRequestURL().toString();
        String args = Arrays.toString(joinPoint.getArgs());
        Integer uid = getCurrentAdminId();
        String ip = ServletUtil.getClientIP(request);
        try {
            // 执行请求方法
            return joinPoint.proceed(joinPoint.getArgs());
        } finally {
            // 记录结束时间
            long endTime = System.currentTimeMillis();
            // {METHOD} - {URL} - ARGS : {} - UID : {} - IP : {} - SPEND TIME : {}
            log.info(LOG_TEMPLATE, method, url, args, uid, ip, endTime - startTime);
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
