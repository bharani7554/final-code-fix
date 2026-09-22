package com.training.codingstandards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilTest {

    @Test
    void hashIdentifierIsDeterministicAndNonEmpty() {
        String first = SecurityUtil.hashIdentifier("1001asha@example.com");
        String second = SecurityUtil.hashIdentifier("1001asha@example.com");

        assertEquals(first, second);
        assertEquals(64, first.length());
        assertNotEquals(first, SecurityUtil.hashIdentifier("different"));
    }

    @Test
    void nullIdentifierProducesEmptyHash() {
        assertEquals("", SecurityUtil.hashIdentifier(null));
    }

    @Test
    void sessionTokensAreNonEmptyAndDifferent() {
        String first = SecurityUtil.sessionToken();
        String second = SecurityUtil.sessionToken();

        assertFalse(first.isBlank());
        assertFalse(second.isBlank());
        assertNotEquals(first, second);
    }

    @Test
    void adminCheckFailsClosedWhenEnvironmentSecretIsMissing() {
        assertFalse(SecurityUtil.isAdmin(null));
        assertFalse(SecurityUtil.isAdmin("anything"));
    }
}
