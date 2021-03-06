package com.daddy.concurrent.utils;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolUtils
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/7 14:31
 * @Version 1.0
 **/
public class ThreadPoolUtils {
    /** 队列的选取
      *无界队列。使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所 有 corePoolSize 线程都忙时新任务在队列中等待。这样，创建的线程就不会超过 corePoolSize。
     * （因 此，maximumPoolSize 的值也就无效了。）当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列；
     * 例如， 在 Web 页服务器中。这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
     *
     * 有界队列。当使用有限的 maximumPoolSizes 时，有界队列 （如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。
     * 队列大小和最大池大小可能需要相互折衷：使用大型队列和小型 池可以最大限度地降低 CPU 使用率、
     * 操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。如果任务频繁阻塞（例如，如果它们是 I/O 边 界），则系统可能为超过您许可的更多线程安排时间。
     * 使用小型队列通常要求较大的池大小，CPU 使用率较高，但是可能遇到不可接受的调度开销，这样也会降 低吞吐量。
     * */

    public static ThreadPoolExecutor getThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, int queueCapacity) {
        ThreadPoolExecutor executors = new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //抛弃任务队列中最先加入队列的，再把这个新任务添加进去
                        if (!executor.isShutdown()) {
                            System.out.println("我踢了个任务");
                            executor.getQueue().poll();
                            executor.execute(r);
                        }
                    }
                });
        return executors;

    }
}
