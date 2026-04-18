package org.example;

import java.util.function.BiFunction;
import java.util.function.Function;

/*
 * Complete revision program for BiFunction and andThen()
 *
 * Covers:
 * 1. Basic BiFunction with apply()
 * 2. BiFunction with andThen()
 * 3. String example with andThen()
 * 4. Realistic salary/bonus example
 * 5. Exception cases
 *
 * Important revision note:
 * andThen() accepts a Function, not another BiFunction.
 */
public class BiFunctionAndThenRevision {

    public static void main(String[] args) {

        System.out.println("========== 1. BASIC BiFunction ==========");
        basicBiFunctionExample();

        System.out.println("\n========== 2. BiFunction WITH andThen ==========");
        biFunctionWithAndThenExample();

        System.out.println("\n========== 3. STRING EXAMPLE ==========");
        stringExample();

        System.out.println("\n========== 4. REAL-WORLD EXAMPLE ==========");
        realWorldExample();

        System.out.println("\n========== 5. EXCEPTION CASES ==========");
        exceptionExamples();
    }

    /*
     * Example 1:
     * Basic BiFunction usage with apply()
     *
     * BiFunction<T, U, R>
     * T = type of first input
     * U = type of second input
     * R = return type
     */
    public static void basicBiFunctionExample() {

        // Adds two integers
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

        // Multiplies two integers
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;

        // apply() executes the BiFunction with two input values
        int sumResult = add.apply(10, 20);
        int multiplyResult = multiply.apply(5, 4);

        System.out.println("Addition result = " + sumResult);
        System.out.println("Multiplication result = " + multiplyResult);
    }

    /*
     * Example 2:
     * Using andThen() with BiFunction
     *
     * Syntax:
     * BiFunction<T, U, R> andThen(Function<? super R, ? extends V> after)
     *
     * First BiFunction runs
     * Then the Function given inside andThen() runs on the output
     */
    public static void biFunctionWithAndThenExample() {

        // First step: add two numbers
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

        // Second step: double the result
        Function<Integer, Integer> doubleValue = result -> result * 2;

        // Chain both operations:
        // first add(a, b), then double the returned result
        BiFunction<Integer, Integer, Integer> addThenDouble = add.andThen(doubleValue);

        int finalResult = addThenDouble.apply(5, 3);

        System.out.println("First add, then double = " + finalResult);

        // Another example: multiply first, then triple the result
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;
        Function<Integer, Integer> tripleValue = result -> result * 3;

        BiFunction<Integer, Integer, Integer> multiplyThenTriple = multiply.andThen(tripleValue);

        System.out.println("First multiply, then triple = " + multiplyThenTriple.apply(2, 3));
    }

    /*
     * Example 3:
     * String example for easy understanding
     *
     * First combine two strings
     * Then convert final string to uppercase
     */
    public static void stringExample() {

        // Joins two strings
        BiFunction<String, String, String> joinStrings = (s1, s2) -> s1 + s2;

        // Converts string to uppercase
        Function<String, String> toUpperCase = str -> str.toUpperCase();

        // First join the strings, then convert to uppercase
        BiFunction<String, String, String> joinThenUpper = joinStrings.andThen(toUpperCase);

        String result = joinThenUpper.apply("hello", " world");

        System.out.println("Joined and uppercased string = " + result);
    }

    /*
     * Example 4:
     * Real-world example
     *
     * Scenario:
     * 1. Calculate gross amount after adding bonus
     * 2. Apply tax deduction on the result
     */
    public static void realWorldExample() {

        /*
         * salary = first input
         * bonusPercent = second input
         *
         * grossAmount = salary + (salary * bonusPercent / 100)
         */
        BiFunction<Double, Double, Double> calculateGrossAmount =
                (salary, bonusPercent) -> salary + (salary * bonusPercent / 100);

        // Deduct 10% tax from the gross amount
        Function<Double, Double> deductTax = gross -> gross - (gross * 0.10);

        // First calculate gross amount, then deduct tax
        BiFunction<Double, Double, Double> grossThenTax =
                calculateGrossAmount.andThen(deductTax);

        double finalAmount = grossThenTax.apply(50000.0, 20.0);

        System.out.println("Final amount after bonus and tax = " + finalAmount);
    }

    /*
     * Example 5:
     * Exception-related revision points
     *
     * Key notes:
     * 1. If andThen(null) is used, NullPointerException occurs
     * 2. If the after-function throws exception, it is propagated to caller
     */
    public static void exceptionExamples() {

        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

        // Case 1: Passing null inside andThen()
        try {
            BiFunction<Integer, Integer, Integer> invalid = add.andThen(null);
            System.out.println(invalid.apply(2, 3));
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught: andThen() cannot take null");
        }

        // Case 2: Exception inside after-function
        try {
            Function<Integer, Integer> riskyFunction = value -> value / (value - 5);

            // First add, then run risky function
            BiFunction<Integer, Integer, Integer> riskyBiFunction = add.andThen(riskyFunction);

            // 2 + 3 = 5, then 5 / (5 - 5) => divide by zero
            System.out.println("Risky result = " + riskyBiFunction.apply(2, 3));
        } catch (ArithmeticException e) {
            System.out.println("ArithmeticException caught: " + e.getMessage());
        }
    }
}