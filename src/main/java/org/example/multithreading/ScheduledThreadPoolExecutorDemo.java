package org.example.multithreading;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolExecutor Demo
 * ---------------------------------
 * This example demonstrates:
 * 1. schedule(Runnable, delay, unit) : runs a task once after the given delay.
 * 2. schedule(Callable<V>, delay, unit) : runs a task once after delay and returns a value through ScheduledFuture<V>.
 * 3. scheduleAtFixedRate(Runnable, initialDelay, period, unit) : runs periodically at a fixed rate from the start time, so the schedule tries to keep exact intervals
 * 4. scheduleWithFixedDelay(Runnable, initialDelay, delay, unit) : waits for one run to finish, then waits the delay before starting the next run.
 *
 * Why ScheduledThreadPoolExecutor?
 * - Use it when tasks must run later or repeatedly.
 * - It is better than manually sleeping threads.
 * - It is designed for delayed and periodic execution.
 *
 * Interview note:
 * - schedule(): one-time execution after delay
 * - scheduleAtFixedRate(): repeated execution at fixed rate from start time
 * - scheduleWithFixedDelay(): repeated execution after previous run finishes + delay
 */
public class ScheduledThreadPoolExecutorDemo {

    public static void main(String[] args) throws Exception {

        // Create scheduler with 2 threads
        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(2);

        // ---------------------------------------------------
        // 1) schedule(Runnable, delay, unit)
        // ---------------------------------------------------
        // Use case:
        // - Run a task once after a delay
        // - Example: send email after 5 seconds, delayed cleanup, retry after delay
        ScheduledFuture<?> oneTimeTask = scheduler.schedule(() -> {
            System.out.println("One-time task executed by: " + Thread.currentThread().getName());
        }, 3, TimeUnit.SECONDS);

        // ---------------------------------------------------
        // 2) schedule(Callable<V>, delay, unit)
        // ---------------------------------------------------
        // Use case:
        // - Run once after a delay and return a result
        // - Example: delayed computation, delayed config fetch
        ScheduledFuture<String> callableTask = scheduler.schedule(() -> {
            System.out.println("Callable task executed by: " + Thread.currentThread().getName());
            return "Callable result";
        }, 4, TimeUnit.SECONDS);

        // ---------------------------------------------------
        // 3) scheduleAtFixedRate(Runnable, initialDelay, period, unit)
        // ---------------------------------------------------
        // Use case:
        // - Run a task repeatedly at fixed intervals
        // - Time is measured from the scheduled start time
        // - If a task takes longer than period, next run may start late
        // - Good for monitoring, polling, metrics collection
        ScheduledFuture<?> fixedRateTask = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Fixed-rate task started by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000); // simulate task work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Fixed-rate task completed");
        }, 1, 5, TimeUnit.SECONDS);

        // ---------------------------------------------------
        // 4) scheduleWithFixedDelay(Runnable, initialDelay, delay, unit)
        // ---------------------------------------------------
        // Use case:
        // - Run again only after previous execution finishes + delay
        // - Good for polling systems where you want gap after completion
        // - Safer when task duration is variable
        ScheduledFuture<?> fixedDelayTask = scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Fixed-delay task started by: " + Thread.currentThread().getName());
            try {
                Thread.sleep(2000); // simulate task work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Fixed-delay task completed");
        }, 1, 5, TimeUnit.SECONDS);

        // Wait for one-time tasks to complete
        System.out.println("One-time runnable result = " + oneTimeTask.get());
        System.out.println("Callable result = " + callableTask.get());

        // Let periodic tasks run for a while
        Thread.sleep(15000);

        // Cancel periodic tasks because they run forever until cancelled
        fixedRateTask.cancel(true);
        fixedDelayTask.cancel(true);

        // Shutdown scheduler
        scheduler.shutdown();
    }
}

/*        | Method                   | Timing style                       | Best for                           |
        | ------------------------ | ---------------------------------- | ---------------------------------- |
        | scheduleAtFixedRate()    | Based on start times               | Polling, monitoring, heartbeats    |
        | scheduleWithFixedDelay() | Delay starts after task completion | Tasks with variable execution time |*/
