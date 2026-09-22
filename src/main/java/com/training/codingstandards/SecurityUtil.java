package com.training.codingstandards;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HexFormat;

/**
 * Small security helpers used by the training application.
 */
public final class SecurityUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String API_KEY_PREFIX =
            System.getenv().getOrDefault("REPORT_API_KEY_PREFIX", "training");

    private SecurityUtil() {
        // Utility class.
    }

    public static String hashIdentifier(String value) {
        if (value == null) {
            return "";
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 is unavailable", ex);
        }
    }

    public static String sessionToken() {
        byte[] token = new byte[24];
        RANDOM.nextBytes(token);
        return HexFormat.of().formatHex(token) + API_KEY_PREFIX;
    }

    public static boolean isAdmin(String password) {
        String expected = System.getenv("REPORT_ADMIN_PASSWORD");
        return expected != null && password != null
                && MessageDigest.isEqual(
                        expected.getBytes(StandardCharsets.UTF_8),
                        password.getBytes(StandardCharsets.UTF_8));
    }
}
