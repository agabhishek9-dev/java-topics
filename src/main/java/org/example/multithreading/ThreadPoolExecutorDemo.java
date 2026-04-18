package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor Demo
 * -----------------------
 * This example demonstrates:
 * 1. Core and max pool size
 * 2. Work queue behavior
 * 3. allowCoreThreadTimeOut(true)
 * 4. Custom ThreadFactory
 * 5. Custom RejectedExecutionHandler
 *
 * Interview points:
 * - ThreadPoolExecutor has default behaviors available.
 * - Default thread factory creates normal threads.
 * - Default rejection handlers like AbortPolicy are already present.
 * - Here we use custom implementations to understand and control behavior better.
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {

        /**
        // Using default thread factory and default abort policy provided by Executors/ThreadPoolExecutor
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.HOURS, new ArrayBlockingQueue<>(10), 
                                                                 Executors.defaultThreadFactory(), 
                                                                 new ThreadPoolExecutor.AbortPolicy());
       */

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,                           // core pool size
                5,                           // maximum pool size
                60,                          // keep-alive time
                TimeUnit.SECONDS,            // keep-alive unit
                new ArrayBlockingQueue<>(3), // blocking queue capacity
                new CustomThreadFactory(),   // custom thread factory
                new CustomRejectedHandler()  // custom rejection handler
        );

        // Allow even core threads to terminate if idle for keep-alive time
        threadPoolExecutor.allowCoreThreadTimeOut(true);

        for (int i = 1; i <= 10; i++) {
            final int taskNumber = i;

            threadPoolExecutor.submit(() -> {
                try {
                    Thread.sleep(5000); // simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(
                        "Task " + taskNumber + " executed by: " +
                        Thread.currentThread().getName()
                );
            });
        }

        threadPoolExecutor.shutdown();
    }
}

/**
 * Custom ThreadFactory
 * --------------------
 * Used to customize thread creation.
 * Here we give each thread a meaningful name.
 */
class CustomThreadFactory implements ThreadFactory {

    private int threadCount = 1;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("Custom-Thread-" + threadCount++);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}

/**
 * Custom RejectedExecutionHandler
 * -------------------------------
 * Called when the thread pool and queue are full.
 * Instead of using the default AbortPolicy, we handle rejection ourselves.
 */
class CustomRejectedHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(
                "Task rejected. Active threads = " + executor.getActiveCount() +
                ", Queue size = " + executor.getQueue().size()
        );
    }
}
