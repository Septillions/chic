package com.github.chic.common.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;

/**
 * 项目工具类
 */
public class ProjectUtils {
    /**
     * 是否为开发环境
     */
    public static Boolean isDevEnv() {
        return isCurrentEnv("dev");
    }

    /**
     * 是否为测试环境
     */
    public static Boolean isTestEnv() {
        return isCurrentEnv("test");
    }

    /**
     * 是否为生产环境
     */
    public static Boolean isProdEnv() {
        return isCurrentEnv("prod");
    }

    /**
     * 是否为当前运行环境
     */
    public static Boolean isCurrentEnv(String env) {
        String[] activeProfiles = SpringUtil.getActiveProfiles();
        if (ArrayUtil.isNotEmpty(activeProfiles)) {
            for (String activeProfile : activeProfiles) {
                if (env.equals(activeProfile)) {
                    return true;
                }
            }
        }
        return false;
    }
}
