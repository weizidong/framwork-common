package org.weizidong.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 日志工具类
 *
 * @author WeiZiDong
 */
public class LogUtil {
    private static Logger log = LogManager.getLogger(LogUtil.class);

    /**
     * 是否开启Debug
     */
    private static boolean isDebug() {
        return log.isDebugEnabled();
    }

    /**
     * 获取日志输出对象
     *
     * @param clazz 目标.Class
     * @return 日志输出对象
     */
    public static Logger getLogger(Class clazz) {
        return LogManager.getLogger(clazz);
    }

    /**
     * debug 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void debug(Class clazz, Object message) {
        if (isDebug()) {
            LogManager.getLogger(clazz).debug(JSON.toJSONString(message));
        }
    }

    /**
     * debug 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息格式
     * @param params  输出信息参数
     */
    public static void debug(Class clazz, String message, Object... params) {
        if (isDebug()) {
            LogManager.getLogger(clazz).debug(message, params);
        }
    }

    /**
     * debug 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     * @param t       堆栈信息
     */
    public static void debug(Class clazz, String message, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).debug(message, t);
        }
    }

    /**
     * debug 输出
     *
     * @param clazz 目标.Class
     * @param t     堆栈信息
     */
    public static void debug(Class clazz, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).debug(t.getMessage(), t);
        }
    }


    /**
     * info 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void info(Class clazz, Object message) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(JSON.toJSONString(message));
        }
    }

    /**
     * info 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息格式
     * @param params  输出信息参数
     */
    public static void info(Class clazz, String message, Object... params) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(message, params);
        }
    }

    /**
     * info 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     * @param t       堆栈信息
     */
    public static void info(Class clazz, String message, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(message, t);
        }
    }

    /**
     * info 输出
     *
     * @param clazz 目标.Class
     * @param t     堆栈信息
     */
    public static void info(Class clazz, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(t.getMessage(), t);
        }
    }

    /**
     * warn 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void warn(Class clazz, Object message) {
        if (isDebug()) {
            LogManager.getLogger(clazz).warn(JSON.toJSONString(message));
        }
    }

    /**
     * warn 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息格式
     * @param params  输出信息参数
     */
    public static void warn(Class clazz, String message, Object... params) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(message, params);
        }
    }

    /**
     * warn 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     * @param t       堆栈信息
     */
    public static void warn(Class clazz, String message, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(message, t);
        }
    }

    /**
     * warn 输出
     *
     * @param clazz 目标.Class
     * @param t     堆栈信息
     */
    public static void warn(Class clazz, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).info(t.getMessage(), t);
        }
    }

    /**
     * error 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     */
    public static void error(Class clazz, Object message) {
        if (isDebug()) {
            LogManager.getLogger(clazz).error(JSON.toJSONString(message));
        }
    }

    /**
     * error 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息格式
     * @param params  输出信息参数
     */
    public static void error(Class clazz, String message, Object... params) {
        if (isDebug()) {
            LogManager.getLogger(clazz).error(message, params);
        }
    }

    /**
     * error 输出
     *
     * @param clazz   目标.Class
     * @param message 输出信息
     * @param t       堆栈信息
     */
    public static void error(Class clazz, String message, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).error(message, t);
        }
    }

    /**
     * error 输出
     *
     * @param clazz 目标.Class
     * @param t     堆栈信息
     */
    public static void error(Class clazz, Throwable t) {
        if (isDebug()) {
            LogManager.getLogger(clazz).error(t.getMessage(), t);
        }
    }
}
