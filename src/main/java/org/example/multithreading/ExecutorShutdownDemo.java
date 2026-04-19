package org.example.multithreading;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService Shutdown Demo
 * -----------------------------
 * This example shows:
 * 1. shutdown()
 * 2. awaitTermination()
 * 3. shutdownNow()
 *
 * Why these methods are used:
 * - shutdown(): stop accepting new tasks, but allow submitted tasks to finish.
 * - awaitTermination(): block current thread until executor finishes or timeout happens.
 * - shutdownNow(): try to stop tasks immediately and return pending tasks.
 *
 * Interview note:
 * - shutdown() is graceful.
 * - shutdownNow() is forceful.
 * - awaitTermination() is used after shutdown() or shutdownNow() to wait for completion.
 */
public class ExecutorShutdownDemo {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        // ---------------------------------------------------
        // Submit a long-running task
        // ---------------------------------------------------
        executor.submit(() -> {
            try {
                System.out.println("Task-1 started by " + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println("Task-1 completed");
            } catch (InterruptedException e) {
                System.out.println("Task-1 interrupted");
                Thread.currentThread().interrupt();
            }
        });

        // ---------------------------------------------------
        // Submit another task
        // ---------------------------------------------------
        executor.submit(() -> {
            try {
                System.out.println("Task-2 started by " + Thread.currentThread().getName());
                Thread.sleep(8000);
                System.out.println("Task-2 completed");
            } catch (InterruptedException e) {
                System.out.println("Task-2 interrupted");
                Thread.currentThread().interrupt();
            }
        });

        // ---------------------------------------------------
        // shutdown()
        // ---------------------------------------------------
        // Use case:
        // - When you want to stop taking new tasks
        // - But allow already submitted tasks to complete normally
        executor.shutdown();
        System.out.println("shutdown() called: no new tasks will be accepted.");

        // ---------------------------------------------------
        // submit after shutdown -> RejectedExecutionException
        // ---------------------------------------------------
        try {
            executor.submit(() -> System.out.println("This task will be rejected"));
        } catch (Exception e) {
            System.out.println("Task rejected after shutdown: " + e.getClass().getSimpleName());
        }

        // ---------------------------------------------------
        // awaitTermination()
        // ---------------------------------------------------
        // Use case:
        // - Wait for graceful completion
        // - Helpful in main methods, cleanup logic, and application shutdown
        try {
            boolean terminated = executor.awaitTermination(3, TimeUnit.SECONDS);

            if (terminated) {
                System.out.println("Executor terminated within timeout.");
            } else {
                System.out.println("Executor did not terminate in time.");
            }
        } catch (InterruptedException e) {
            System.out.println("awaitTermination interrupted");
            Thread.currentThread().interrupt();
        }

        // ---------------------------------------------------
        // shutdownNow()
        // ---------------------------------------------------
        // Use case:
        // - Forcefully stop tasks if graceful shutdown is taking too long
        // - Interrupt running tasks
        // - Return tasks that were waiting in queue and never started
        if (!executor.isTerminated()) {
            List<Runnable> notStartedTasks = executor.shutdownNow();
            System.out.println("shutdownNow() called.");
            System.out.println("Pending tasks returned by shutdownNow(): " + notStartedTasks.size());
        }

        // Final wait after shutdownNow
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Executor still not terminated after shutdownNow.");
            } else {
                System.out.println("Executor fully terminated.");
            }
        } catch (InterruptedException e) {
            System.out.println("Final awaitTermination interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
