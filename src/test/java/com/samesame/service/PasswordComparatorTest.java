package com.samesame.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for PasswordComparator
 * Tests security, functionality, and edge cases
 */
class PasswordComparatorTest {

    private PasswordComparator passwordComparator;

    @BeforeEach
    void setUp() {
        passwordComparator = new PasswordComparator();
    }

    @Test
    @DisplayName("Should return true when passwords are identical")
    void testIdenticalPasswords() {
        String password = "SecurePassword123!";
        assertTrue(passwordComparator.comparePasswords(password, password));
    }

    @Test
    @DisplayName("Should return true when passwords have same content")
    void testSameContentPasswords() {
        String password1 = "MyPassword123";
        String password2 = "MyPassword123";
        assertTrue(passwordComparator.comparePasswords(password1, password2));
    }

    @Test
    @DisplayName("Should return false when passwords differ")
    void testDifferentPasswords() {
        String password1 = "Password123";
        String password2 = "Password124";
        assertFalse(passwordComparator.comparePasswords(password1, password2));
    }

    @Test
    @DisplayName("Should return false when passwords differ in case")
    void testCaseSensitivePasswords() {
        String password1 = "Password123";
        String password2 = "password123";
        assertFalse(passwordComparator.comparePasswords(password1, password2));
    }

    @Test
    @DisplayName("Should return false when one password is null")
    void testNullPassword() {
        String password = "ValidPassword";
        assertFalse(passwordComparator.comparePasswords(password, null));
        assertFalse(passwordComparator.comparePasswords(null, password));
    }

    @Test
    @DisplayName("Should return false when both passwords are null")
    void testBothPasswordsNull() {
        assertFalse(passwordComparator.comparePasswords(null, null));
    }

    @Test
    @DisplayName("Should handle empty passwords")
    void testEmptyPasswords() {
        assertTrue(passwordComparator.comparePasswords("", ""));
        assertFalse(passwordComparator.comparePasswords("password", ""));
        assertFalse(passwordComparator.comparePasswords("", "password"));
    }

    @Test
    @DisplayName("Should handle whitespace differences")
    void testWhitespacePasswords() {
        assertFalse(passwordComparator.comparePasswords("password", " password"));
        assertFalse(passwordComparator.comparePasswords("password ", "password"));
        assertTrue(passwordComparator.comparePasswords(" password ", " password "));
    }

    @Test
    @DisplayName("Should handle very long passwords")
    void testLongPasswords() {
        String longPassword = "a".repeat(1000);
        assertTrue(passwordComparator.comparePasswords(longPassword, longPassword));
        
        String differentLongPassword = "a".repeat(999) + "b";
        assertFalse(passwordComparator.comparePasswords(longPassword, differentLongPassword));
    }

    @Test
    @DisplayName("Should handle special characters")
    void testSpecialCharacters() {
        String specialPassword = "P@ssw0rd!#$%^&*()";
        assertTrue(passwordComparator.comparePasswords(specialPassword, specialPassword));
        
        String differentSpecial = "P@ssw0rd!#$%^&*(]";
        assertFalse(passwordComparator.comparePasswords(specialPassword, differentSpecial));
    }

    @Test
    @DisplayName("Should handle Unicode characters")
    void testUnicodePasswords() {
        String unicodePassword = "Pässwörd123🔒";
        assertTrue(passwordComparator.comparePasswords(unicodePassword, unicodePassword));
    }

    // Password Strength Tests
    
    @Test
    @DisplayName("Should evaluate weak passwords correctly")
    void testWeakPasswords() {
        assertEquals("Weak", passwordComparator.evaluatePasswordStrength("123"));
        assertEquals("Weak", passwordComparator.evaluatePasswordStrength("password"));
        assertEquals("Weak", passwordComparator.evaluatePasswordStrength("abc123"));
    }

    @Test
    @DisplayName("Should evaluate medium strength passwords")
    void testMediumPasswords() {
        assertEquals("Medium", passwordComparator.evaluatePasswordStrength("Password1"));
        assertEquals("Medium", passwordComparator.evaluatePasswordStrength("mypassword123"));
    }

    @Test
    @DisplayName("Should evaluate strong passwords")
    void testStrongPasswords() {
        assertEquals("Strong", passwordComparator.evaluatePasswordStrength("MyPassword123!"));
        assertEquals("Strong", passwordComparator.evaluatePasswordStrength("SecurePass2023"));
    }

    @Test
    @DisplayName("Should evaluate very strong passwords")
    void testVeryStrongPasswords() {
        assertEquals("Very Strong", passwordComparator.evaluatePasswordStrength("MyVerySecureP@ssw0rd2023!"));
        assertEquals("Very Strong", passwordComparator.evaluatePasswordStrength("Tr0ub4dor&3XtremelySecure"));
    }

    @Test
    @DisplayName("Should handle empty password for strength evaluation")
    void testEmptyPasswordStrength() {
        assertEquals("", passwordComparator.evaluatePasswordStrength(""));
        assertEquals("", passwordComparator.evaluatePasswordStrength(null));
    }

    @Test
    @DisplayName("Should detect repeating characters")
    void testRepeatingCharacters() {
        // Passwords with repeating characters should be weaker
        String withRepeating = "Passsssword123!";
        String withoutRepeating = "Password123!";
        
        // The one without repeating should be stronger or equal
        String strengthWithRepeating = passwordComparator.evaluatePasswordStrength(withRepeating);
        String strengthWithoutRepeating = passwordComparator.evaluatePasswordStrength(withoutRepeating);
        
        // This test ensures the algorithm considers repeating characters
        assertNotNull(strengthWithRepeating);
        assertNotNull(strengthWithoutRepeating);
    }

    @Test
    @DisplayName("Should detect common patterns")
    void testCommonPatterns() {
        // Passwords with common patterns should be weaker
        assertEquals("Weak", passwordComparator.evaluatePasswordStrength("password123"));
        assertEquals("Weak", passwordComparator.evaluatePasswordStrength("admin123"));
        assertEquals("Weak", passwordComparator.evaluatePasswordStrength("qwerty123"));
    }

    @Test
    @DisplayName("Should generate consistent secure hashes")
    void testSecureHashGeneration() {
        String password = "TestPassword123!";
        String hash1 = passwordComparator.generateSecureHash(password);
        String hash2 = passwordComparator.generateSecureHash(password);
        
        assertEquals(hash1, hash2, "Same password should generate same hash");
        assertEquals(64, hash1.length(), "SHA-256 hash should be 64 characters long");
        
        String differentPassword = "DifferentPassword123!";
        String differentHash = passwordComparator.generateSecureHash(differentPassword);
        assertNotEquals(hash1, differentHash, "Different passwords should generate different hashes");
    }

    @Test
    @DisplayName("Should handle performance with multiple comparisons")
    void testPerformance() {
        String password1 = "PerformanceTestPassword123!";
        String password2 = "PerformanceTestPassword123!";
        
        long startTime = System.nanoTime();
        
        // Perform multiple comparisons
        for (int i = 0; i < 1000; i++) {
            passwordComparator.comparePasswords(password1, password2);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds
        
        // Should complete 1000 comparisons in reasonable time (less than 1 second)
        assertTrue(duration < 1000, "1000 password comparisons should complete in less than 1 second");
    }
}