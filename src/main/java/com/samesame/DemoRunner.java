package com.samesame;

import com.samesame.service.PasswordComparator;

/**
 * Demo runner that automatically demonstrates the password comparison functionality
 * This runs without user input to showcase the application's capabilities
 */
public class DemoRunner {
    
    private static final PasswordComparator passwordComparator = new PasswordComparator();
    
    public static void main(String[] args) {
        printWelcome();
        runAutomatedDemo();
        printConclusion();
    }
    
    private static void printWelcome() {
        System.out.println("=".repeat(70));
        System.out.println("           SameSame - Secure Password Comparator DEMO");
        System.out.println("=".repeat(70));
        System.out.println("Demonstrating secure password comparison with various test cases...");
        System.out.println("Features: Constant-time comparison, Memory clearing, Strength evaluation");
        System.out.println("=".repeat(70));
        System.out.println();
    }
    
    private static void runAutomatedDemo() {
        // Test cases with descriptions
        TestCase[] testCases = {
            new TestCase("Password123!", "Password123!", "Identical strong passwords"),
            new TestCase("Password123!", "Password123", "Different passwords (missing special char)"),
            new TestCase("password123", "Password123", "Case sensitivity test"),
            new TestCase("MySecureP@ssw0rd2023!", "MySecureP@ssw0rd2023!", "Very strong password match"),
            new TestCase("weak", "weak", "Weak password match"),
            new TestCase("", "", "Empty passwords"),
            new TestCase("Test123!", "test123!", "Case difference test"),
            new TestCase("LongPasswordWithManyCharacters123!", "LongPasswordWithManyCharacters123!", "Long password test"),
            new TestCase("Special@#$%^&*()Chars", "Special@#$%^&*()Chars", "Special characters test"),
            new TestCase("password", "admin123", "Completely different passwords")
        };
        
        for (int i = 0; i < testCases.length; i++) {
            TestCase testCase = testCases[i];
            
            System.out.println("Test " + (i + 1) + ": " + testCase.description);
            System.out.println("-".repeat(50));
            
            // Display passwords (masked for security demo)
            System.out.println("Password 1: " + maskPassword(testCase.password1));
            System.out.println("Password 2: " + maskPassword(testCase.password2));
            
            // Evaluate password strength for first password
            String strength = passwordComparator.evaluatePasswordStrength(testCase.password1);
            if (!strength.isEmpty()) {
                System.out.println("Strength:   " + strength + " " + getStrengthIndicator(strength));
            }
            
            // Perform secure comparison
            long startTime = System.nanoTime();
            boolean match = passwordComparator.comparePasswords(testCase.password1, testCase.password2);
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1_000_000.0;
            
            // Display result
            System.out.println("Result:     " + (match ? "✓ PASSWORDS MATCH" : "✗ PASSWORDS DO NOT MATCH"));
            System.out.printf("Time:       %.3f ms (constant-time algorithm)%n", durationMs);
            
            // Security demonstration
            if (i == 0) {
                demonstrateSecurity(testCase.password1);
            }
            
            System.out.println();
        }
    }
    
    private static void demonstrateSecurity(String password) {
        System.out.println("Security Demo:");
        
        // Show hash generation
        String hash = passwordComparator.generateSecureHash(password);
        System.out.println("  SHA-256 Hash: " + hash.substring(0, 16) + "... (truncated for display)");
        
        // Demonstrate constant-time property
        System.out.println("  ✓ Constant-time comparison prevents timing attacks");
        System.out.println("  ✓ Memory clearing prevents data leakage");
        System.out.println("  ✓ No password storage or logging");
    }
    
    private static String maskPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "(empty)";
        }
        
        if (password.length() <= 3) {
            return "*".repeat(password.length());
        }
        
        // Show first and last character with asterisks in between
        return password.charAt(0) + "*".repeat(password.length() - 2) + password.charAt(password.length() - 1);
    }
    
    private static String getStrengthIndicator(String strength) {
        switch (strength.toLowerCase()) {
            case "weak":
                return "🔴";
            case "medium":
                return "🟡";
            case "strong":
                return "🟢";
            case "very strong":
                return "🔵";
            default:
                return "";
        }
    }
    
    private static void printConclusion() {
        System.out.println("=".repeat(70));
        System.out.println("                        DEMO COMPLETED");
        System.out.println("=".repeat(70));
        System.out.println("Key Features Demonstrated:");
        System.out.println("  ✓ Secure password comparison using constant-time algorithms");
        System.out.println("  ✓ Password strength evaluation with multiple criteria");
        System.out.println("  ✓ Memory security with automatic data clearing");
        System.out.println("  ✓ Protection against timing attacks");
        System.out.println("  ✓ Support for special characters and Unicode");
        System.out.println("  ✓ Fast performance (sub-millisecond comparisons)");
        System.out.println();
        System.out.println("To run the interactive version:");
        System.out.println("  java -cp build\\classes com.samesame.ConsolePasswordComparator");
        System.out.println();
        System.out.println("For the GUI version, install JavaFX and run:");
        System.out.println("  java --module-path path\\to\\javafx\\lib --add-modules javafx.controls,javafx.fxml");
        System.out.println("       -cp build\\classes com.samesame.PasswordComparatorApp");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Simple test case container
     */
    private static class TestCase {
        final String password1;
        final String password2;
        final String description;
        
        TestCase(String password1, String password2, String description) {
            this.password1 = password1;
            this.password2 = password2;
            this.description = description;
        }
    }
}