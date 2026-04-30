package org.example.multithreading;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletableFuture Demo
 * ----------------------
 * This example demonstrates:
 * 1. supplyAsync()
 * 2. thenApply()
 * 3. thenApplyAsync()
 * 4. thenComposeAsync()
 * 5. thenAccept()
 * 6. thenCombine()
 *
 * Interview points:
 * - supplyAsync() starts an async task that returns a value.
 * - thenApply() transforms the result of one stage.
 * - thenApplyAsync() transforms the result asynchronously.
 * - thenComposeAsync() is used for dependent async tasks and avoids nested futures.
 * - thenAccept() consumes the result and returns CompletableFuture<Void>.
 * - thenCombine() combines results of two independent futures.
 */
public class CompletableFutureDemo {

    public static void main(String[] args) {

        // Creating ExecutorService from Executors Utility Methods
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // ---------------------------------------------------
        // 1) supplyAsync()
        // Starts async task and returns a result
        // ---------------------------------------------------
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "task completed...";
        }, executorService);

        try {
            System.out.println(future1.get()); // Prints async result
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------
        // 2) thenApply()
        // Transforms the result synchronously in the same chain.
        // Used for simple transformations. It takes the result of the previous stage and applies a function to it
        // ---------------------------------------------------
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "Hello, Abhishek ";
        }, executorService).thenApply(item -> item + "Gupta");

        try {
            System.out.println(future2.get()); // Prints transformed value
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------
        // 3) thenApplyAsync()
        // Transforms the result asynchronously
        // ---------------------------------------------------
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            return "This is completable future - ";
        }, executorService).thenApplyAsync(item -> item + "thenApplyAsync method");

        try {
            System.out.println(future3.get()); // Prints async transformed value
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------
        // 4) thenComposeAsync()
        // Used for dependent async stages.
        // Returns flattened CompletableFuture instead of nested future.
        // Used when the next step in your chain also returns a CompletableFuture. It "flattens" the nested futures into one
        // ---------------------------------------------------
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> {
            return "Step 1 is done";
        }, executorService).thenComposeAsync(val -> CompletableFuture.supplyAsync(() -> {
            return "Step 2 is done";
        }, executorService));

        try {
            System.out.println(future4.get()); // Prints composed async result
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------
        // 5) thenAccept()
        // Consumes final result and returns CompletableFuture<Void>.
        // Used when you want to use the result (e.g., printing to console, saving to a database) but don't need to 
        // pass a value further down the chain
        // ---------------------------------------------------
        CompletableFuture<Void> future5 = CompletableFuture.supplyAsync(() -> {
            return "Concept and ";
        }, executorService).thenAccept(val -> System.out.println(val + "All stages completed"));

        try {
            future5.get(); // Wait until consumer finishes
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ---------------------------------------------------
        // 6) thenCombine()
        // Combines results of two independent futures
        // Used to run two independent futures in parallel and combine their results once both are complete.
        // ---------------------------------------------------
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> 10, executorService);
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "k", executorService);

        CompletableFuture<String> combinedFuture = task1.thenCombine(
                task2,
                (val1, val2) -> val1 + val2
        );

        try {
            System.out.println(combinedFuture.get()); // Prints combined result
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}

/*
--- What each method does ---

supplyAsync() starts an asynchronous computation that returns a value.
thenApply() converts the result of one stage into another value, similar to map(). Uses same thread
thenApplyAsync() does the same transformation, but on another thread asynchronously.
thenComposeAsync() is used when the next async stage depends on the previous result and returns another CompletableFuture.
thenAccept() consumes the result and does not return a value, so it ends that branch of the chain.
thenCombine() merges the results of two independent async tasks into one result.

--- Why use them ---
Use thenApply() when you only need to transform a single result.
Use thenCompose() or thenComposeAsync() when one async task depends on another.
Use thenAccept() when you only want to print, log, or store the final value.
Use thenCombine() when two tasks can run independently and their results must be merged later.*/
