package com.samesame.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Service class for secure password comparison and strength evaluation
 */
public class PasswordComparator {

    // Patterns for password strength evaluation
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern DIGITS = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHARS = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':,.<>?]");

    /**
     * Securely compares two passwords using constant-time comparison
     * to prevent timing attacks
     * 
     * @param password1 First password to compare
     * @param password2 Second password to compare
     * @return true if passwords match exactly, false otherwise
     */
    public boolean comparePasswords(String password1, String password2) {
        if (password1 == null || password2 == null) {
            return false;
        }
        
        // Convert to char arrays for secure handling
        char[] chars1 = password1.toCharArray();
        char[] chars2 = password2.toCharArray();
        
        try {
            // Use constant-time comparison to prevent timing attacks
            boolean result = constantTimeEquals(chars1, chars2);
            return result;
        } finally {
            // Clear sensitive data from memory
            clearArray(chars1);
            clearArray(chars2);
        }
    }

    /**
     * Performs constant-time comparison of two character arrays
     * This prevents timing attacks by ensuring the comparison always
     * takes the same amount of time regardless of where differences occur
     */
    private boolean constantTimeEquals(char[] a, char[] b) {
        if (a.length != b.length) {
            return false;
        }
        
        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        
        return result == 0;
    }

    /**
     * Securely clears a character array by overwriting with zeros
     */
    private void clearArray(char[] array) {
        if (array != null) {
            Arrays.fill(array, '\0');
        }
    }

    /**
     * Evaluates the strength of a password based on various criteria
     * 
     * @param password The password to evaluate
     * @return String representation of password strength
     */
    public String evaluatePasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }
        
        int score = 0;
        int length = password.length();
        
        // Length scoring
        if (length >= 8) score++;
        if (length >= 12) score++;
        if (length >= 16) score++;
        
        // Character variety scoring
        if (LOWERCASE.matcher(password).find()) score++;
        if (UPPERCASE.matcher(password).find()) score++;
        if (DIGITS.matcher(password).find()) score++;
        if (SPECIAL_CHARS.matcher(password).find()) score++;
        
        // Additional complexity checks
        if (hasNoRepeatingChars(password)) score++;
        if (hasNoCommonPatterns(password)) score++;
        
        // Determine strength based on score
        if (score <= 2) {
            return "Weak";
        } else if (score <= 4) {
            return "Medium";
        } else if (score <= 6) {
            return "Strong";
        } else {
            return "Very Strong";
        }
    }

    /**
     * Checks if password has no more than 2 consecutive repeating characters
     */
    private boolean hasNoRepeatingChars(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            if (password.charAt(i) == password.charAt(i + 1) && 
                password.charAt(i + 1) == password.charAt(i + 2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks for common weak patterns in passwords
     */
    private boolean hasNoCommonPatterns(String password) {
        String lowerPassword = password.toLowerCase();
        
        // Check for common weak patterns
        String[] weakPatterns = {
            "123", "abc", "qwe", "asd", "zxc",
            "password", "admin", "user", "login",
            "000", "111", "222", "333"
        };
        
        for (String pattern : weakPatterns) {
            if (lowerPassword.contains(pattern)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Generates a secure hash of the password for additional security operations
     * Note: This is not used for comparison but could be useful for future features
     */
    public String generateSecureHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating secure hash", e);
        }
    }
}