package org.weizidong.common.utils;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 异步工具类
 *
 * @author WeiZiDong
 */
public class ThreadPoolUtil {
    private ThreadPoolUtil() {
    }

    /**
     * 创建可缓存的线程池
     */
    private static ExecutorService cachedPool = Executors.newCachedThreadPool();
    /**
     * 创建定时以及周期性执行任务的线程池（1个线程）
     */
    private static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);

    /**
     * 马上执行异步方法 <br/>
     * <code>ThreadPoolUtil.execute(()->{
     * System.out.print("方法体");
     * })</code>
     *
     * @param runnable 方法体
     */
    public static void execute(Runnable runnable) {
        if (cachedPool.isShutdown()) {
            cachedPool = Executors.newCachedThreadPool();
        }
        cachedPool.execute(runnable);
    }

    /**
     * 定时任务
     *
     * @param runnable 方法体
     * @param date     执行的时间，必须是未来的时刻，否者马上执行
     * @return 任务对象
     */
    public static ScheduledFuture<?> schedule(Runnable runnable, Date date) {
        if (scheduledPool.isShutdown()) {
            scheduledPool = Executors.newScheduledThreadPool(2);
        }
        Long time = date.getTime() - System.currentTimeMillis();
        return scheduledPool.schedule(runnable, time > 0 ? time : 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟任务
     *
     * @param runnable 执行的方法体
     * @param time     延迟的时间，必须大于0，否则马上执行
     * @param unit     时间单位
     * @return 任务对象
     */
    public static ScheduledFuture<?> schedule(Runnable runnable, int time, TimeUnit unit) {
        if (scheduledPool.isShutdown()) {
            scheduledPool = Executors.newScheduledThreadPool(2);
        }
        return scheduledPool.schedule(runnable, time > 0 ? time : 0, unit);
    }

    /**
     * 延迟任务
     *
     * @param runnable 执行的方法体
     * @param time     延迟的时间(单位 秒)，必须大于0，否则马上执行
     * @return 任务对象
     */
    public static ScheduledFuture<?> scheduleSeconds(Runnable runnable, int time) {
        if (scheduledPool.isShutdown()) {
            scheduledPool = Executors.newScheduledThreadPool(2);
        }
        return scheduledPool.schedule(runnable, time > 0 ? time : 0, TimeUnit.SECONDS);
    }

    /**
     * 延迟任务
     *
     * @param runnable 执行的方法体
     * @param time     延迟的时间(单位 小时)，必须大于0，否则马上执行
     * @return 任务对象
     */
    public static ScheduledFuture<?> scheduleHours(Runnable runnable, int time) {
        if (scheduledPool.isShutdown()) {
            scheduledPool = Executors.newScheduledThreadPool(2);
        }
        return scheduledPool.schedule(runnable, time > 0 ? time : 0, TimeUnit.HOURS);
    }

    /**
     * 延迟任务
     *
     * @param runnable 执行的方法体
     * @param time     延迟的时间(单位 天)，必须大于0，否则马上执行
     * @return 任务对象
     */
    public static ScheduledFuture<?> scheduleDays(Runnable runnable, int time) {
        if (scheduledPool.isShutdown()) {
            scheduledPool = Executors.newScheduledThreadPool(2);
        }
        return scheduledPool.schedule(runnable, time > 0 ? time : 0, TimeUnit.DAYS);
    }


}
