package org.wzd.framwork.utils;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 异步工具类
 *
 * @author WeiZiDong
 */
public class ThreadPoolUtil {
	// 创建可缓存的线程池
	private static ExecutorService cachedPool = Executors.newCachedThreadPool();
	// 创建定时以及周期性执行任务的线程池（1个线程）
	private static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);

	/**
	 * 马上执行异步方法 <br/>
	 * <code>ThreadPoolUtil.execute(()->{
	 *          System.out.print("方法体");
	 *      })</code>
	 * 
	 * @param runnable
	 *            方法体
	 */
	public static void execute(Runnable runnable) {
		if (cachedPool.isShutdown()) {
			cachedPool = Executors.newCachedThreadPool();
		}
		cachedPool.execute(runnable);
	}

	/**
	 * 定时任务
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
	 */
	public static ScheduledFuture<?> schedule(Runnable runnable, Integer time) {
		if (scheduledPool.isShutdown()) {
			scheduledPool = Executors.newScheduledThreadPool(2);
		}
		return scheduledPool.schedule(runnable, time != null && time > 0 ? time : 0, TimeUnit.SECONDS);
	}

}
