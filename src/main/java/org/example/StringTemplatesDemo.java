package org.example;

import java.util.StringJoiner;

import static java.util.FormatProcessor.FMT;

public class StringTemplatesDemo {
    public static void main(String[] args) {
        String name = "Abhishek";
        int age = 28;
        double salary = 85000.50;

        System.out.println("=== 1. TRADITIONAL APPROACHES ===");
        traditionalApproaches(name, age, salary);

        System.out.println("\n=== 2. JAVA 21 STRING TEMPLATES (STR) ===");
        java21StringTemplates(name, age, salary);

        System.out.println("\n=== 3. MULTILINE + COMPLEX EXAMPLES ===");
        multilineExamples();

        System.out.println("\n=== 4. CUSTOM TEMPLATE PROCESSOR ===");
        customTemplateProcessor();
    }

    // 1. Traditional approaches (pre-Java 21)
    static void traditionalApproaches(String name, int age, double salary) {
        // String concatenation
        System.out.println("Concat: Hello " + name + ", age: " + age);

        // String.format()
        System.out.println("Format: Hello %s, salary: %.2f".formatted(name, salary));

        // StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("Employee: ").append(name).append(", Salary: ").append(salary);
        System.out.println("StringBuilder: " + sb.toString());
    }

    // 2. Java 21 String Templates - STR processor
    static void java21StringTemplates(String name, int age, double salary) {
        // STR."template \{variable}" syntax
        String greeting = STR."Hello \{name}!";
        System.out.println(greeting);

        String details = STR."Employee: \{name}, Age: \{age}, Salary: Rs.\{salary}";
        System.out.println(details);

        // Method calls and expressions
        String calc = STR."Bonus: \{salary * 0.15}";
        System.out.println(calc);

        // Null-safe
        String nullSafe = STR."Name: \{name != null ? name : "Unknown"}";
        System.out.println(nullSafe);
    }

    // 3. Multiline templates (perfect for JSON, HTML, SQL)
    static void multilineExamples() {
        String customerName = "Abhishek Gupta";
        String phone = "9876543210";
        String city = "Bengaluru";

        // JSON example
        String json = STR."""
            {
                "name": "\{customerName}",
                "phone": "\{phone}",
                "city": "\{city}",
                "active": true
            }
            """;
        System.out.println("JSON:\n" + json);

        // HTML table
        String table = STR."""
            <table>
                <tr><td>Name</td><td>\{customerName}</td></tr>
                <tr><td>Phone</td><td>\{phone}</td></tr>
            </table>
            """;
        System.out.println("HTML:\n" + table);
    }

    // 4. Custom Template Processor (FMT example)
    static void customTemplateProcessor() {
        double price = 2999.99;
        int qty = 3;

        // FMT processor with formatting
        String formatted = FMT."Total: Rs.%.2f (Qty: %d)".formatted(price * qty, qty);
        System.out.println(formatted);

        // Custom processor for currency
        StringTemplate.Processor<String, RuntimeException> CURRENCY = st -> {
            StringBuilder result = new StringBuilder();
            var fragments = st.fragments();
            var values = st.values();

            var iterFrag = fragments.iterator();
            var iterVal = values.iterator();

            while (iterFrag.hasNext() && iterVal.hasNext()) {
                result.append(iterFrag.next());
                Object value = iterVal.next();
                result.append(String.format("₹%.2f", Double.parseDouble(value.toString())));
            }
            result.append(iterFrag.next());
            return result.toString();
        };

        String currency = CURRENCY."Price: \{price}, Total: \{price * qty}";
        System.out.println("Custom Currency: " + currency);
    }
}


/*
=== 1. TRADITIONAL APPROACHES ===
Concat: Hello Abhishek, age: 28
Format: Hello Abhishek, salary: 85000.50
StringBuilder: Employee: Abhishek, Salary: 85000.5

        === 2. JAVA 21 STRING TEMPLATES (STR) ===
Hello Abhishek!
Employee: Abhishek, Age: 28, Salary: Rs.85000.5
Bonus: 12750.075
Name: Abhishek

=== 3. MULTILINE + COMPLEX EXAMPLES ===
JSON:
        {
        "name": "Abhishek Gupta",
        "phone": "9876543210",
        "city": "Bengaluru",
        "active": true
        }

        === 4. CUSTOM TEMPLATE PROCESSOR ===
Total: Rs.8999.97 (Qty: 3)
Custom Currency: Price: ₹2999.99, Total: ₹8999.97*/
