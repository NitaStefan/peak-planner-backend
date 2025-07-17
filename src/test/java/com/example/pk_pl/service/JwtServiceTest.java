package com.example.pk_pl.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // set a constant secret key for tests
        String secret = "MySecretKey12345678901234567890123456789012"; // 48 bytes base64 -> 32 bytes
        ReflectionTestUtils.setField(jwtService, "secretKey", java.util.Base64.getEncoder().encodeToString(secret.getBytes()));
        ReflectionTestUtils.setField(jwtService, "accessTokenExp", 1000L);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExp", 2000L);
    }

    @Test
    void generateAndValidateAccessToken() {
        String token = jwtService.generateAccessToken("user@example.com");
        assertNotNull(token);
        String username = jwtService.extractUsername(token);
        assertEquals("user@example.com", username);
    }

    @Test
    void tokenValidation() {
        String token = jwtService.generateAccessToken("test@user.com");
        // user details implementation not needed, use simple implementation
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User("test@user.com", "pass", java.util.Collections.emptyList());
        assertTrue(jwtService.isValid(token, user));
    }
}
