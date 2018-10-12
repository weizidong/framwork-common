package org.weizidong.common.utils;

import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.ConnectException;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.Date;

/**
 * 日志打印
 *
 * @author WeiZiDong
 * @date 2018-07-23
 */
public class LogUtil {
  private LogUtil() {
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
   * 日志打印
   */
  private static void print(TextArea dialog, String msg) {
    Platform.runLater(() -> {
      if (dialog == null) {
        return;
      }
      dialog.insertText(0, DateUtil.format(new Date(), DateUtil.P_TIMESTAMP) + "\t" + msg + "\n");
      dialog.setScrollTop(0);
      String str = dialog.getText();
      String[] strs = str.split("\n");
      if (strs.length > 1000) {
        int last = str.lastIndexOf("\n");
        dialog.deleteText(last - strs[strs.length - 1].length() - 1, last);
      }
    });
  }

  /**
   * 打印错误
   */
  public static void error(Class clazz, Throwable t, String pattern, Object... arguments) {
    Logger logger = LogManager.getLogger(clazz);
    if (logger.isErrorEnabled()) {
      error(clazz, t, MessageFormat.format(pattern, arguments));
    }
  }

  /**
   * 记录异常
   */
  public static void error(Class clazz, Throwable t, String msg) {
    Logger logger = LogManager.getLogger(clazz);
    if (logger.isErrorEnabled()) {
      if (t instanceof ConnectException || t instanceof SocketException) {
        logger.error(msg);
      } else {
        logger.error(msg, t);
      }
    }
  }

  /**
   * 记录异常
   */
  public static void error(Class clazz, Throwable t) {
    Logger logger = LogManager.getLogger(clazz);
    if (logger.isErrorEnabled()) {
      logger.error(t.getMessage(), t);
    }
  }

  /**
   * 打印日志
   */
  public static void info(Class clazz, String pattern, Object... arguments) {
    Logger logger = LogManager.getLogger(clazz);
    if (logger.isInfoEnabled()) {
      if (arguments.length > 0) {
        logger.info(pattern, arguments);
      } else {
        logger.info(pattern);
      }
    }
  }

  /**
   * debug日志
   */
  public static void debug(Class clazz, String pattern, Object... arguments) {
    Logger logger = LogManager.getLogger(clazz);
    if (logger.isDebugEnabled()) {
      if (arguments.length > 0) {
        logger.debug(pattern, arguments);
      } else {
        logger.debug(pattern);
      }
    }
  }

  /**
   * debug日志
   */
  public static void debug(Class clazz, Object message) {
    Logger logger = LogManager.getLogger(clazz);
    if (logger.isDebugEnabled()) {
      logger.debug(message);
    }
  }
}
