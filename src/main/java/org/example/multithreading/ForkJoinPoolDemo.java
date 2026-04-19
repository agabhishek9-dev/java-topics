package org.example.multithreading;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool Demo
 * -----------------
 * This example calculates the sum of numbers from start to end using
 * divide-and-conquer parallelism.
 *
 * Why ForkJoinPool?
 * - It is designed for recursive tasks.
 * - It uses work-stealing to keep threads busy.
 * - It is useful when a big task can be split into smaller tasks.
 *
 * Why RecursiveTask?
 * - Use RecursiveTask when the task returns a result.
 * - Use RecursiveAction when the task does not return a result.
 */
public class ForkJoinPoolDemo {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // ForkJoinPool.commonPool()
        // ---------------------------------------------------
        // commonPool() gives a shared pool managed by JVM.
        // It is useful for simple demos and common parallel tasks.
        ForkJoinPool pool = ForkJoinPool.commonPool();

        // ---------------------------------------------------
        // Create the task
        // ---------------------------------------------------
        // We want to calculate sum from 1 to 100.
        // The task itself will recursively split the range.
        ComputeSumTask task = new ComputeSumTask(1, 100);

        // ---------------------------------------------------
        // submit/invoke the task
        // ---------------------------------------------------
        // invoke() submits the task and waits for the result.
        // It is the simplest way when you need the final computed value.
        int result = pool.invoke(task);

        System.out.println("Final sum = " + result);
    }
}

/**
 * RecursiveTask is used when the task returns a value.
 * Here the returned value is Integer because we are calculating sum.
 */
class ComputeSumTask extends RecursiveTask<Integer> {

    // Start of the range
    private final int start;

    // End of the range
    private final int end;

    // Threshold decides when to stop splitting and compute directly
    // If the range is small, do sequential work instead of creating more subtasks.
    private static final int THRESHOLD = 10;

    public ComputeSumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        // ---------------------------------------------------
        // Base case: small enough to compute directly
        // ---------------------------------------------------
        // Why? Because creating subtasks has overhead.
        // For small ranges, sequential computation is faster.
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }

        // ---------------------------------------------------
        // Split the task into two subtasks
        // ---------------------------------------------------
        // Divide and conquer:
        // 1. Split the range
        // 2. Fork subtasks
        // 3. Join results
        int mid = (start + end) / 2;

        ComputeSumTask leftTask = new ComputeSumTask(start, mid);
        ComputeSumTask rightTask = new ComputeSumTask(mid + 1, end);

        // ---------------------------------------------------
        // fork()
        // ---------------------------------------------------
        // fork() schedules the task asynchronously in the pool.
        // The task may go into the work-stealing queue.
        leftTask.fork();
        rightTask.fork();

        // ---------------------------------------------------
        // join()
        // ---------------------------------------------------
        // join() waits for the result of the forked task.
        // It returns the computed value.
        int leftResult = leftTask.join();
        int rightResult = rightTask.join();

        // Combine both partial results
        return leftResult + rightResult;
    }
}

/*--- The Key Difference ---

Feature	            RecursiveTask<V>	                        RecursiveAction
Return Value	    Returns a result of type V.	                Returns nothing (void).
Main Method	        protected V compute()	                    protected void compute()
Analogy	            Like a Getter: You ask it to calculate      Like a Setter: You tell it to "go do this work" (e.g., update an array).
                    something and give it back.	*/
