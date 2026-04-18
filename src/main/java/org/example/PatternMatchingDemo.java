package org.example;

/**
 * Shape hierarchy using records + sealed interfaces (Java 17+ pattern)
 */
sealed interface Shape permits Circle, Rectangle, Point {}
record Point(int x, int y) implements Shape {}
record Circle(Point center, double radius) implements Shape {}
record Rectangle(double width, double height) implements Shape {}

// Main demo class
public class PatternMatchingDemo {
    /**
     * Calculate area using record patterns in switch expression
     */
    public static double area(Object shape) {
        return switch (shape) {
            case Rectangle(double w, double h) -> w * h;                    // Destructure
            case Circle(Point(int x, int y), double r) -> Math.PI * r * r;  // Nested!
            case Point(int x, int y) -> 0.0;
            case null -> 0.0;                                              // Null safe
            default -> throw new IllegalArgumentException("Unknown shape");
        };
    }

    /**
     * Advanced: Guards (when clauses) + multiple variables
     */
    public static String classify(Object shape) {
        return switch (shape) {
            case Rectangle(double w, double h) when (w == h) -> "Square";
            case Rectangle(double w, double h) when (w > 10 || h > 10) -> "Large";
            case Circle(_, double r) when (r > 5) -> "Big Circle";  // _ wildcard
            case Point(int x, int y) when (x > 0 && y > 0) -> "Quadrant I";
            case null -> "No shape";
            default -> "Other";
        };
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println("Area Rectangle(4,5): " + area(new Rectangle(4,5)));     // 20.0
        System.out.println("Area Circle((1,2),3): " + area(new Circle(new Point(1,2), 3))); // ~28.27
        System.out.println("Classify Square: " + classify(new Rectangle(5,5)));     // Square
        System.out.println("Classify Large: " + classify(new Rectangle(20,3)));     // Large
        System.out.println("Null: " + classify(null));                             // No shape
    }
}
