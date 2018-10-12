package org.weizidong.common.utils;

/**
 * 线程工具
 *
 * @author WeiZiDong
 * @date 2018-07-27
 */
public class RuntimeUtil {
  /**
   * 重启
   */
  public static void restart(String cmd) {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        Runtime.getRuntime().exec(cmd);
      } catch (Exception e) {
        LogUtil.error(RuntimeUtil.class, e, "重启失败！");
      }
    }));
    System.exit(0);
  }

  /**
   * 线程沉睡
   */
  public static void sleep(long seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException ignored) {
    }
  }
}
