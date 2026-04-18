package org.example;

/**
 * Functional Interface Demo using Lambda Expression
 * -------------------------------------------------
 * This example shows how a lambda expression can implement
 * a functional interface with two input parameters.
 *
 * Interview points:
 * - A functional interface has exactly one abstract method.
 * - @FunctionalInterface is optional, but it helps the compiler
 *   verify that the interface has only one abstract method.
 * - Lambda expressions reduce boilerplate code compared to
 *   anonymous inner classes.
 */
@FunctionalInterface
interface Addition {

    /**
     * Abstract method to add two numbers.
     * Lambda expression will provide the implementation.
     */
    int add(int a, int b);
}

public class Lambda {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // Step 1: Implement the functional interface using lambda
        // ---------------------------------------------------
        // (a, b) -> a + b
        // 'a' and 'b' are input parameters
        // 'a + b' is the expression returned by the lambda
        Addition result = (a, b) -> a + b;

        // ---------------------------------------------------
        // Step 2: Call the abstract method
        // ---------------------------------------------------
        int res = result.add(2, 5);

        // ---------------------------------------------------
        // Step 3: Print the output
        // ---------------------------------------------------
        System.out.println("Addition result = " + res);
    }
}