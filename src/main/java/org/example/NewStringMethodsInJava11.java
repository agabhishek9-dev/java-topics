package org.example;

public class NewStringMethodsInJava11 {
    public static void main(String[] args) {
        System.out.println("=== JAVA 11+ STRING METHODS DEMO ===\n");

        demoIsBlank();
        demoStripMethods();
        demoRepeat();
        demoLines();
        demoIndent();
        demoTransform();
        demoChars();
        demoCodePoints();
    }

    // 1. isBlank() - Java 11
    // Returns true if string is empty OR contains only whitespace
    static void demoIsBlank() {
        System.out.println("1. isBlank() - Java 11");
        System.out.println("\"\".isBlank(): " + "".isBlank());           // true
        System.out.println(" \"  \".isBlank(): " + "   ".isBlank());     // true
        System.out.println(" \"a\".isBlank(): " + "a".isBlank());        // false
        System.out.println();
    }

    // 2. strip(), stripLeading(), stripTrailing() - Java 11
    // Unicode-aware whitespace removal (vs trim() which is ASCII-only)
    static void demoStripMethods() {
        System.out.println("2. strip(), stripLeading(), stripTrailing() - Java 11");

        String str = "\u2000Java\u2001";  // Unicode whitespace chars

        System.out.println("Original: '" + str + "'");
        System.out.println("strip(): '" + str.strip() + "'");           // "Java"
        System.out.println("stripLeading(): '" + str.stripLeading() + "'"); // "Java\u2001"
        System.out.println("stripTrailing(): '" + str.stripTrailing() + "'"); // "\u2000Java"
        System.out.println("trim(): '" + str.trim() + "'");             // "Java\u2001" (trim misses Unicode!)
        System.out.println();
    }

    // 3. repeat(int count) - Java 11
    // Repeats string N times
    static void demoRepeat() {
        System.out.println("3. repeat() - Java 11");
        String str = "Hi ";
        System.out.println("repeat(3): '" + str.repeat(3) + "'");       // "Hi Hi Hi "
        System.out.println("repeat(0): '" + str.repeat(0) + "'");       // ""
        // System.out.println(str.repeat(-1)); // IllegalArgumentException
        System.out.println();
    }

    // 4. lines() - Java 11
    // Returns Stream<String> of lines (splits on line terminators)
    static void demoLines() {
        System.out.println("4. lines() - Java 11");
        String multiline = "Line 1\nLine 2\rLine 3\n\nLine 5";

        multiline.lines().forEach(line ->
                System.out.println("Line: '" + line + "'")
        );
        // Output:
        // Line: 'Line 1'
        // Line: 'Line 2'
        // Line: 'Line 3'
        // Line: 'Line 5'
        System.out.println();
    }

    // 5. indent(int n) - Java 12
    // Adds/removes indentation (spaces only)
    static void demoIndent() {
        System.out.println("5. indent() - Java 12");
        String code = "public class Test {}";

        System.out.println("Original: '" + code + "'");
        System.out.println("indent(4): '" + code.indent(4) + "'");
        // Output: "    public class Test {}"

        String indentedCode = "    public class Test {}";
        System.out.println("indent(0): '" + indentedCode.indent(0) + "'");
        // Output: "public class Test {}" (removes 4 spaces)
        System.out.println();
    }

    // 6. transform() - Java 12
    // Applies UnaryOperator<String> (useful for mutable transformations)
    static void demoTransform() {
        System.out.println("6. transform() - Java 12");
        String str = " hello ";

        String result = str.transform(s ->
                s.strip().toUpperCase()
        );
        System.out.println("transform(): '" + result + "'");  // "HELLO"
        System.out.println();
    }

    // 7. chars() - Returns IntStream of char values (16-bit)
    static void demoChars() {
        System.out.println("7. chars()");
        String str = "Hi👋";

        System.out.print("chars(): ");
        str.chars().forEach(c -> System.out.print(c + " "));
        // Output: 72 105 128075 (Hi + 👋 as single 16-bit code unit)
        System.out.println();

        System.out.print("As chars: ");
        str.chars().forEach(c -> System.out.print((char)c + " "));
        System.out.println();
    }

    // 8. codePoints() - Returns IntStream of Unicode code points (32-bit)
    static void demoCodePoints() {
        System.out.println("8. codePoints()");
        String str = "Hi👋";

        System.out.print("codePoints(): ");
        str.codePoints().forEach(cp -> System.out.print(cp + " "));
        // Output: 72 105 128075 (Hi + 👋 - correct Unicode!)
        System.out.println();

        System.out.print("As chars: ");
        str.codePoints()
                .forEach(cp -> System.out.print(Character.toString(cp) + " "));
        System.out.println();
    }
}

/*        | Method       | Java Version | Key Difference        | Example                                 |
        | ------------ | ------------ | --------------------- | --------------------------------------- |
        | isBlank()    | 11           | Empty + whitespace    | "   ".isBlank() → true                  |
        | strip()      | 11           | Unicode whitespace    | "\\u2000Hi\\u2001".strip() → "Hi"       |
        | repeat(n)    | 11           | Repeat string N times | "Hi".repeat(3) → "HiHiHi"               |
        | lines()      | 11           | Stream of lines       | "A\\nB".lines() → Stream["A", "B"]      |
        | indent(n)    | 12           | Add/remove spaces     | "code".indent(4) → "    code"           |
        | transform()  | 12           | Apply function        | s.transform(String::toUpperCase)        |
        | chars()      | All          | 16-bit char stream    | "Hi👋".chars() → [72, 105, 128075]      |
        | codePoints() | All          | 32-bit Unicode        | "Hi👋".codePoints() → [72, 105, 128075] |*/
