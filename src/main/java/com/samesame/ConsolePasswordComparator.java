package com.samesame;

import com.samesame.service.PasswordComparator;
import java.io.Console;
import java.util.Scanner;

/**
 * Console-based version of the Password Comparator
 * This version runs without JavaFX dependencies for systems where JavaFX is not available
 */
public class ConsolePasswordComparator {
    
    private static final PasswordComparator passwordComparator = new PasswordComparator();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        printWelcome();
        
        while (true) {
            try {
                runPasswordComparison();
                
                System.out.print("\nWould you like to compare another set of passwords? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                
                if (!choice.equals("y") && !choice.equals("yes")) {
                    break;
                }
                
                System.out.println(); // Add spacing
            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
            }
        }
        
        System.out.println("\nThank you for using SameSame Password Comparator!");
        scanner.close();
    }
    
    private static void printWelcome() {
        System.out.println("=".repeat(60));
        System.out.println("           SameSame - Secure Password Comparator");
        System.out.println("=".repeat(60));
        System.out.println("A secure tool for comparing passwords to ensure they match.");
        System.out.println("Features: Constant-time comparison, Memory clearing, Strength evaluation");
        System.out.println("=".repeat(60));
        System.out.println();
    }
    
    private static void runPasswordComparison() {
        String password1 = getPasswordInput("Enter the first password: ");
        String password2 = getPasswordInput("Enter the second password: ");
        
        // Evaluate password strength
        String strength = passwordComparator.evaluatePasswordStrength(password1);
        if (!strength.isEmpty()) {
            System.out.println("\nPassword strength: " + strength);
        }
        
        // Compare passwords
        boolean isMatch = passwordComparator.comparePasswords(password1, password2);
        
        System.out.println("\n" + "=".repeat(40));
        if (isMatch) {
            System.out.println("✓ RESULT: Passwords MATCH!");
            System.out.println("The passwords are identical.");
        } else {
            System.out.println("✗ RESULT: Passwords DO NOT MATCH!");
            System.out.println("The passwords are different.");
        }
        System.out.println("=".repeat(40));
        
        // Clear sensitive data (the service already handles this, but being explicit)
        password1 = null;
        password2 = null;
        System.gc(); // Suggest garbage collection
    }
    
    private static String getPasswordInput(String prompt) {
        Console console = System.console();
        
        if (console != null) {
            // Use console for hidden password input
            char[] passwordChars = console.readPassword(prompt);
            String password = new String(passwordChars);
            
            // Clear the char array for security
            java.util.Arrays.fill(passwordChars, ' ');
            
            return password;
        } else {
            // Fallback to regular input (password will be visible)
            System.out.print(prompt + "(Warning: Password will be visible) ");
            return scanner.nextLine();
        }
    }
    
    /**
     * Demonstrates the password comparison functionality with test cases
     */
    public static void runDemo() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           DEMO MODE - Test Cases");
        System.out.println("=".repeat(50));
        
        // Test cases
        String[][] testCases = {
            {"Password123!", "Password123!", "Identical passwords"},
            {"Password123!", "Password123", "Different passwords (missing !)"},
            {"password123", "Password123", "Case sensitivity test"},
            {"", "", "Empty passwords"},
            {"Weak123", "Weak123", "Weak password test"},
            {"VerySecureP@ssw0rd2023!", "VerySecureP@ssw0rd2023!", "Strong password test"}
        };
        
        for (int i = 0; i < testCases.length; i++) {
            String pwd1 = testCases[i][0];
            String pwd2 = testCases[i][1];
            String description = testCases[i][2];
            
            System.out.println("\nTest " + (i + 1) + ": " + description);
            System.out.println("Password 1: " + (pwd1.isEmpty() ? "(empty)" : "*".repeat(pwd1.length())));
            System.out.println("Password 2: " + (pwd2.isEmpty() ? "(empty)" : "*".repeat(pwd2.length())));
            
            boolean match = passwordComparator.comparePasswords(pwd1, pwd2);
            String strength = passwordComparator.evaluatePasswordStrength(pwd1);
            
            System.out.println("Result: " + (match ? "✓ MATCH" : "✗ NO MATCH"));
            if (!strength.isEmpty()) {
                System.out.println("Strength: " + strength);
            }
            System.out.println("-".repeat(30));
        }
        
        System.out.println("\nDemo completed. All tests use secure comparison methods.");
    }
}