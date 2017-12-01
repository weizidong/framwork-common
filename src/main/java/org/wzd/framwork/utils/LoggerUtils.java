package org.wzd.framwork.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Log输出封装
 *
 * @author WeiZiDong
 */
public class LoggerUtils {
    /**
     * 是否开启Debug
     */
    public static boolean isDebug = LogManager.getLogger(LoggerUtils.class).isDebugEnabled();

    /**
     * Debug 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void debug(Class<? extends Object> clazz, Object message) {
        if (!isDebug)
            return;
        Logger logger = LogManager.getLogger(clazz);
        logger.debug(JSON.toJSONString(message));
    }

    /**
     * info 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void info(Class<? extends Object> clazz, Object message) {
        if (!isDebug)
            return;
        Logger logger = LogManager.getLogger(clazz);
        logger.info(JSON.toJSONString(message));
    }

    /**
     * warn 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void warn(Class<? extends Object> clazz, Object message) {
        if (!isDebug)
            return;
        Logger logger = LogManager.getLogger(clazz);
        logger.warn(JSON.toJSONString(message));
    }

    /**
     * Debug 输出
     *
     * @param clazz     目标.Class
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtDebug(Class<? extends Object> clazz, String fmtString, Object... value) {
        if (!isDebug)
            return;
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = String.format(fmtString, value);
        }
        debug(clazz, fmtString);
    }

    /**
     * Error 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     * @param e       异常类
     */
    public static void error(Class<? extends Object> clazz, String message, Exception e) {
        Logger logger = LogManager.getLogger(clazz);
        if (null == e) {
            logger.error(message);
            return;
        }
        logger.error(message, e);
    }

    /**
     * Error 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void error(Class<? extends Object> clazz, String message) {
        error(clazz, message, null);
    }

    /**
     * 异常填充值输出
     *
     * @param clazz     目标.Class
     * @param fmtString 输出信息key
     * @param e         异常类
     * @param value     输出信息value
     */
    public static void fmtError(Class<? extends Object> clazz, Exception e, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = String.format(fmtString, value);
        }
        error(clazz, fmtString, e);
    }

    /**
     * 异常填充值输出
     *
     * @param clazz     目标.Class
     * @param fmtString 输出信息key
     * @param value     输出信息value
     */
    public static void fmtError(Class<? extends Object> clazz, String fmtString, Object... value) {
        if (StringUtils.isBlank(fmtString)) {
            return;
        }
        if (null != value && value.length != 0) {
            fmtString = String.format(fmtString, value);
        }
        error(clazz, fmtString);
    }
}
