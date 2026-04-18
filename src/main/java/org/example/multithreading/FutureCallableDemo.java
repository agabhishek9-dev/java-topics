package org.example;

import java.util.concurrent.*;

/**
 * Future and Callable Demo
 * ------------------------
 * This example shows all 3 overloaded submit() methods:
 *
 * 1. submit(Runnable task)
 * 2. submit(Runnable task, T result)
 * 3. submit(Callable<T> task)
 *
 * Interview points:
 * - Runnable has run() and returns no result.
 * - Callable has call() and returns a value.
 * - Future represents the result of an asynchronous task.
 * - Future.get() blocks until the result is available.
 */
public class FutureCallableDemo {

    public static void main(String[] args) throws Exception {

//        1. Can use this to create threads
/*   ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3), new CustomThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
*/

//      2. Can also use this way to create threads
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // ---------------------------------------------------
        // 1) submit(Runnable)
        // Returns Future<?> because Runnable does not return a value
        // ---------------------------------------------------
        Future<?> future1 = executorService.submit(() -> {
            System.out.println("Runnable task executed by: " + Thread.currentThread().getName());
        });

        System.out.println("submit(Runnable) return = " + future1.get()); // returns null

        // ---------------------------------------------------
        // 2) submit(Runnable, T result)
        // Returns Future<T>, where T is the value you provide
        // ---------------------------------------------------
        Future<String> future2 = executorService.submit(() -> {
            System.out.println("Runnable with result executed by: " + Thread.currentThread().getName());
        }, "Custom Result from Runnable");

        System.out.println("submit(Runnable, T) return = " + future2.get()); // returns provided result

        // ---------------------------------------------------
        // 3) submit(Callable<T>)
        // Callable returns a value from call()
        // ---------------------------------------------------
        Future<Integer> future3 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                System.out.println("Callable task executed by: " + Thread.currentThread().getName());
                return 100;
            }
        });

        System.out.println("submit(Callable) return = " + future3.get()); // returns callable result

        executorService.shutdown();
    }
}

 /*   Runnable task executed by: pool-1-thread-1
    submit(Runnable) return = null
    Runnable with result executed by: pool-1-thread-2
    submit(Runnable, T) return = Custom Result from Runnable
    Callable task executed by: pool-1-thread-3
    submit(Callable) return = 100*/

/*
--- A Note on shutdown() ---
In your original code, the program will stay "hanging" even after printing 20. This is because the threads in the
ThreadPoolExecutor are still alive. Always call threadPoolExecutor.shutdown() to allow the JVM to exit.*/

