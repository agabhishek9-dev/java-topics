package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor Demo
 * -----------------------
 * This example demonstrates:
 * 1. Creating a custom ThreadPoolExecutor
 * 2. Using a custom ThreadFactory
 * 3. Submitting multiple tasks
 * 4. Understanding core pool size, maximum pool size, and queue capacity
 *
 * Interview points:
 * - Core pool size = minimum number of threads kept alive.
 * - Maximum pool size = maximum number of threads the pool can create.
 * - Work queue stores tasks when all core threads are busy.
 * - RejectedExecutionHandler handles tasks when pool and queue are full.
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // Step 1: Create a custom thread pool
        // ---------------------------------------------------
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,                              // core pool size
                5,                              // maximum pool size
                60,                             // keep-alive time
                TimeUnit.SECONDS,               // unit for keep-alive time
                new ArrayBlockingQueue<>(3),    // task queue capacity
                new CustomThreadFactory(),      // custom thread factory
                new ThreadPoolExecutor.AbortPolicy() // rejection policy
        );

        // ---------------------------------------------------
        // Step 2: Submit tasks to the pool
        // ---------------------------------------------------
        for (int i = 1; i <= 7; i++) {
            final int taskNumber = i;

            threadPoolExecutor.submit(() -> {
                try {
                    // Simulate a long-running task
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // Restore interrupt status when interrupted
                    Thread.currentThread().interrupt();
                }

                System.out.println(
                        "Task " + taskNumber + " executed by: " +
                        Thread.currentThread().getName()
                );
            });
        }

        // ---------------------------------------------------
        // Step 3: Stop accepting new tasks
        // ---------------------------------------------------
        threadPoolExecutor.shutdown();
    }
}

/**
 * Custom ThreadFactory
 * --------------------
 * Used to customize thread creation.
 * Common uses:
 * - Set thread name
 * - Set thread priority
 * - Set daemon status
 */
class CustomThreadFactory implements ThreadFactory {

    private int threadCount = 1;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);

        // Give a meaningful name to the thread
        thread.setName("Custom-Thread-" + threadCount++);

        // Set thread priority if needed
        thread.setPriority(Thread.NORM_PRIORITY);

        return thread;
    }
}
