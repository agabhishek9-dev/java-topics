package org.example;

import java.util.Optional;
import java.util.NoSuchElementException;

/**
 * OptionalMethodsDemo
 * -------------------
 * This example demonstrates the most commonly used methods of Optional:
 *
 * Creation methods:
 * - Optional.of()
 * - Optional.ofNullable()
 * - Optional.empty()
 *
 * Read/check methods:
 * - isPresent()
 * - isEmpty()
 * - get()
 * - ifPresent()
 * - ifPresentOrElse()
 *
 * Default value methods:
 * - orElse()
 * - orElseGet()
 * - orElseThrow()
 *
 * Transform methods:
 * - map()
 * - flatMap()
 * - filter()
 *
 * Interview points:
 * - Optional is a container object that may contain a non-null value.
 * - It is mainly used to avoid null checks and improve readability.
 * - Do not use Optional as a field type in normal classes unless there is a strong reason.
 */
public class OptionalMethodsDemo {

    public static void main(String[] args) {

        // ---------------------------------------------------
        // 1. Creating Optional objects
        // ---------------------------------------------------
        Optional<String> opt1 = Optional.of("Abhishek"); // value must not be null
        Optional<String> opt2 = Optional.ofNullable(null); // empty optional
        Optional<String> opt3 = Optional.empty(); // explicitly empty

        System.out.println("opt1 = " + opt1);
        System.out.println("opt2 = " + opt2);
        System.out.println("opt3 = " + opt3);

        // ---------------------------------------------------
        // 2. isPresent() and isEmpty()
        // ---------------------------------------------------
        System.out.println("opt1.isPresent() = " + opt1.isPresent());
        System.out.println("opt2.isPresent() = " + opt2.isPresent());
        System.out.println("opt2.isEmpty() = " + opt2.isEmpty());

        // ---------------------------------------------------
        // 3. get()
        // ---------------------------------------------------
        // get() returns the value if present, otherwise throws exception
        System.out.println("opt1.get() = " + opt1.get());

        // ---------------------------------------------------
        // 4. ifPresent()
        // ---------------------------------------------------
        opt1.ifPresent(value -> System.out.println("ifPresent: " + value));

        // ---------------------------------------------------
        // 5. ifPresentOrElse()
        // ---------------------------------------------------
        opt2.ifPresentOrElse(
                value -> System.out.println("Value present: " + value),
                () -> System.out.println("Value is absent")
        );

        // ---------------------------------------------------
        // 6. orElse()
        // ---------------------------------------------------
        // Returns default value immediately if Optional is empty
        String name1 = opt2.orElse("Default Name");
        System.out.println("orElse = " + name1);

        // ---------------------------------------------------
        // 7. orElseGet()
        // ---------------------------------------------------
        // Default supplier runs only when value is absent
        String name2 = opt2.orElseGet(() -> "Generated Name");
        System.out.println("orElseGet = " + name2);

        // ---------------------------------------------------
        // 8. orElseThrow()
        // ---------------------------------------------------
        try {
            String name3 = opt2.orElseThrow(() -> new NoSuchElementException("No value found"));
            System.out.println(name3);
        } catch (NoSuchElementException e) {
            System.out.println("orElseThrow exception: " + e.getMessage());
        }

        // ---------------------------------------------------
        // 9. map()
        // ---------------------------------------------------
        // Transforms the value if present
        Optional<Integer> lengthOpt = opt1.map(String::length);
        System.out.println("map length = " + lengthOpt);

        // ---------------------------------------------------
        // 10. flatMap()
        // ---------------------------------------------------
        Optional<Person> personOpt = Optional.of(new Person("Abhishek", "Bengaluru"));
        Optional<String> cityOpt = personOpt.flatMap(Person::getCityOptional);
        System.out.println("flatMap city = " + cityOpt);

        // ---------------------------------------------------
        // 11. filter()
        // ---------------------------------------------------
        Optional<String> filtered = opt1.filter(name -> name.startsWith("A"));
        System.out.println("filter startsWith A = " + filtered);

        Optional<String> filtered2 = opt1.filter(name -> name.startsWith("Z"));
        System.out.println("filter startsWith Z = " + filtered2);
    }
}

/**
 * Helper class for flatMap demo.
 */
class Person {
    private final String name;
    private final String city;

    public Person(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public Optional<String> getCityOptional() {
        return Optional.ofNullable(city);
    }

    public String getName() {
        return name;
    }
}