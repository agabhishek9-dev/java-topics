package org.example;

/**
 * Anonymous Class Example
 * -----------------------
 * This example shows how to create an anonymous inner class
 * to implement an interface without writing a separate class.
 *
 * Interview points:
 * - Anonymous class has no name.
 * - It is declared and instantiated at the same time.
 * - Commonly used for one-time use implementations.
 * - Can be used to implement interfaces or extend classes.
 */
interface Multiplication {

    // Single abstract method
    int multiply(int a, int b);
}

public class AnonymousClass {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // Step 1: Create anonymous implementation of interface
        // ---------------------------------------------------
        // Instead of creating a separate class, we directly
        // provide method body here.
        Multiplication testOp = new Multiplication() {

            @Override
            public int multiply(int a, int b) {
                // Return product of two numbers
                return a * b;
            }
        };

        // ---------------------------------------------------
        // Step 2: Call the method
        // ---------------------------------------------------
        int result = testOp.multiply(2, 4);

        // ---------------------------------------------------
        // Step 3: Print the output
        // ---------------------------------------------------
        System.out.println("Multiplication result = " + result);
    }
}