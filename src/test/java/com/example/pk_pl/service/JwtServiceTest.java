package com.example.pk_pl.service;

import com.example.pk_pl.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // set a constant secret key for tests
        String secret = "MySecretKey12345678901234567890123456789012"; // 48 bytes base64 -> 32 bytes
        ReflectionTestUtils.setField(jwtService, "secretKey", java.util.Base64.getEncoder().encodeToString(secret.getBytes()));
        ReflectionTestUtils.setField(jwtService, "accessTokenExp", 10000L);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExp", 20000L);
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

        User user = new User();
        user.setEmail("test@user.com");
        assertTrue(jwtService.isValid(token, user));
    }
}