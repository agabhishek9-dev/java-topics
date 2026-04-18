package org.example;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Common Functional Interfaces in Java
 * ------------------------------------
 * This example demonstrates the most commonly used
 * predefined functional interfaces from java.util.function.
 *
 * Covered interfaces:
 * - Runnable
 * - Consumer<T>
 * - Supplier<T>
 * - Predicate<T>
 * - Function<T, R>
 * - BiFunction<T, U, R>
 *
 * Interview points:
 * - These are built-in functional interfaces used with lambda expressions.
 * - They help reduce boilerplate code.
 * - They are widely used in Streams, collections, and event handling.
 */
public class CommonFunctionalInterfacesInJava {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // 1) Runnable
        // ---------------------------------------------------
        // Takes no input and returns no output.
        Runnable runnable = () -> System.out.println("Task is running..");
        runnable.run();

        // ---------------------------------------------------
        // 2) Consumer<T>
        // ---------------------------------------------------
        // Takes one input and returns nothing.
        Consumer<String> consumer = (String item) -> System.out.println("Item consumed: " + item);
        consumer.accept("Item to be consumed");

        // ---------------------------------------------------
        // 3) Supplier<T>
        // ---------------------------------------------------
        // Takes no input and returns a value.
        Supplier<String> supplier = () -> "Item supplied...";
        System.out.println(supplier.get());

        // ---------------------------------------------------
        // 4) Predicate<T>
        // ---------------------------------------------------
        // Takes one input and returns boolean.
        // Here we check whether the string is empty.
        Predicate<String> predicate = (str) -> str.isEmpty();
        boolean result = predicate.test("Abhishek");
        System.out.println(result);

        // ---------------------------------------------------
        // 5) Function<T, R>
        // ---------------------------------------------------
        // Takes one input and returns one output.
        Function<Integer, Integer> function = x -> x * x;
        Integer apply = function.apply(2);
        System.out.println(apply);

        // ---------------------------------------------------
        // 6) BiFunction<T, U, R>
        // ---------------------------------------------------
        // Takes two inputs and returns one output.
        BiFunction<Integer, Integer, Integer> biFunction = (a, b) -> a + b;
        Integer apply1 = biFunction.apply(3, 7);
        System.out.println(apply1);

        // ---------------------------------------------------
        // 7) andThen() with Function/BiFunction result
        // ---------------------------------------------------
        // biFunction.apply(9, 5) = 14
        // then function.apply(14) = 196
        BiFunction<Integer, Integer, Integer> addThenDouble = biFunction.andThen(function);
        Integer apply2 = addThenDouble.apply(9, 5);
        System.out.println(apply2);
    }
}

/* --- Output ---
Task is running..
Item consumed: Item to be consumed
Item supplied...
false
4
10
196*/
