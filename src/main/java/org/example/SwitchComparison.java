package org.example;

public class SwitchComparison {
    public static void main(String[] args) {
        String day = "WEDNESDAY";
        int month = 4;

        // TRADITIONAL SWITCH STATEMENT
        System.out.println("=== 1. TRADITIONAL SWITCH STATEMENT ===");
        traditionalSwitch(day);

        // ENHANCED SWITCH EXPRESSION (Java 14+)
        System.out.println("\n=== 2. ENHANCED SWITCH EXPRESSION ===");
        enhancedSwitchExpression(day);

        // MULTIPLE VALUES + YIELD (Java 14+)
        System.out.println("\n=== 3. MULTIPLE VALUES + YIELD ===");
        enhancedSwitchWithYield(month);
    }

    // 1. TRADITIONAL SWITCH - STATEMENT (pre-Java 12)
    static void traditionalSwitch(String day) {
        String result;
        switch (day) {
            case "MONDAY":
            case "TUESDAY":
            case "WEDNESDAY":
            case "THURSDAY":
            case "FRIDAY":
                result = "Weekday";
                break;
            case "SATURDAY":
            case "SUNDAY":
                result = "Weekend";
                break;
            default:
                result = "Invalid day";
                break;
        }
        System.out.println("Traditional: " + result);  // Weekday
    }

    // 2. ENHANCED SWITCH EXPRESSION - DIRECT VALUE (Java 14+)
    static void enhancedSwitchExpression(String day) {
        // Returns value directly - NO break needed!
        String result = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Weekday";
            case "SATURDAY", "SUNDAY" -> "Weekend";
            default -> "Invalid day";
        };
        System.out.println("Enhanced: " + result);  // Weekday
    }

    // 3. ENHANCED SWITCH with YIELD - Multiple statements
    static void enhancedSwitchWithYield(int month) {
        String result = switch (month) {
            case 1, 2, 12 -> {
                // Multiple statements allowed
                String season = "Winter";
                int days = 31;
                yield season + " (" + days + " days)";  // yield returns value
            }
            case 3, 4, 5 -> "Spring";
            case 6, 7, 8 -> "Summer";
            case 9, 10, 11 -> "Fall";
            default -> "Invalid month";
        };
        System.out.println("With yield: " + result);  // Spring (31 days)
    }
}
