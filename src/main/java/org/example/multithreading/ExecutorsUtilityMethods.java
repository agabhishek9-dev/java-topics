package org.example.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService Factory Methods Demo
 * ------------------------------------
 * This example shows:
 * 1. newFixedThreadPool()
 * 2. newCachedThreadPool()
 * 3. newSingleThreadExecutor()
 *
 * Interview notes:
 * - These are factory methods provided by Executors class.
 * - They are easy ways to create commonly used thread pools.
 */
public class ExecutorsUtilityMethods {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // 1) Fixed thread pool
        // ---------------------------------------------------
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        // ---------------------------------------------------
        // 2) Cached thread pool
        // ---------------------------------------------------
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        // ---------------------------------------------------
        // 3) Single thread executor
        // ---------------------------------------------------
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        // Submit a few sample tasks to each executor
        for (int i = 1; i <= 5; i++) {
            final int taskNo = i;

            fixedThreadPool.submit(() ->
                    System.out.println("Fixed pool task " + taskNo + " executed by " + Thread.currentThread().getName())
            );

            cachedThreadPool.submit(() ->
                    System.out.println("Cached pool task " + taskNo + " executed by " + Thread.currentThread().getName())
            );

            singleThreadExecutor.submit(() ->
                    System.out.println("Single pool task " + taskNo + " executed by " + Thread.currentThread().getName())
            );
        }

        // Always shut down executors
        fixedThreadPool.shutdown();
        cachedThreadPool.shutdown();
        singleThreadExecutor.shutdown();
    }
}

/*
        | Executor                  | Min / Core Threads | Max Threads                       | Queue Size                      | Idle Thread Alive Time                  | When to Use                                                              | Advantage                                            | Disadvantage                                          |
        | ------------------------- | ------------------ | --------------------------------- | ------------------------------- | --------------------------------------- | ------------------------------------------------------------------------ | ---------------------------------------------------- | ----------------------------------------------------- |
        | newFixedThreadPool(n)     | n                  | n                                 | Unbounded queue                 | Threads stay alive until shutdown       | When you want a fixed number of worker threads and stable resource usage | Predictable, simple, good for controlled workloads   | Queue can grow large and cause memory pressure        |
        | newCachedThreadPool()     | 0                  | Very high / effectively unbounded | No queue, uses handoff behavior | Idle threads die after about 60 seconds | When tasks are short-lived and arrive unpredictably                      | Good for bursty workloads, creates threads on demand | Can create too many threads and overload the system   |
        | newSingleThreadExecutor() | 1                  | 1                                 | Unbounded queue                 | Thread stays alive until shutdown       | When tasks must run one after another in order                           | Guarantees sequential execution                      | Low throughput, single thread can become a bottleneck |*/
