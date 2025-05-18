package com.helmesbackend.task.helmes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secret", "testSecretKey123456789012345678901234567890");
        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L); // 1 hour
    }

    @Test
    void shouldGenerateValidToken() {
        Long personId = 123L;

        String token = jwtService.generateToken(personId);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length); // JWT has 3 parts separated by dots
    }

    @Test
    void shouldValidateCorrectToken() {
        Long personId = 123L;
        String token = jwtService.generateToken(personId);

        boolean isValid = jwtService.validateToken(token);

        assertTrue(isValid);
    }

    @Test
    void shouldExtractCorrectPersonId() {
        Long personId = 456L;
        String token = jwtService.generateToken(personId);

        Long extractedPersonId = jwtService.extractPersonId(token);

        assertEquals(personId, extractedPersonId);
    }

    @Test
    void shouldRejectInvalidTokens() {

        assertFalse(jwtService.validateToken(null));
        assertFalse(jwtService.validateToken(""));
        assertFalse(jwtService.validateToken("invalid.token.here"));
        assertFalse(jwtService.validateToken("not-a-jwt-at-all"));
    }

    @Test
    void shouldRejectExpiredToken() {
        // Given - Create service with very short expiration
        JwtService shortExpirationService = new JwtService();
        ReflectionTestUtils.setField(shortExpirationService, "secret", "testSecretKey123456789012345678901234567890");
        ReflectionTestUtils.setField(shortExpirationService, "expiration", 1L); // 1 millisecond

        String token = shortExpirationService.generateToken(123L);

        // When - Wait for token to expire
        try {
            Thread.sleep(10); // Wait a bit to ensure expiration
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Then
        assertFalse(shortExpirationService.validateToken(token));
    }

    @Test
    void shouldReturnNullForInvalidTokenExtraction() {
        // Test extracting person ID from invalid tokens
        assertNull(jwtService.extractPersonId(null));
        assertNull(jwtService.extractPersonId("invalid.token"));
        assertNull(jwtService.extractPersonId(""));
    }

}
