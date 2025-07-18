package com.example.pk_pl.service;

import com.example.pk_pl.dao.UserDao;
import com.example.pk_pl.dto.AuthenticationResponse;
import com.example.pk_pl.exception.ConflictException;
import com.example.pk_pl.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private BCryptPasswordEncoder encoder;
    @InjectMocks
    private AuthService authService;

    @Test
    void registerEncodesPasswordAndSavesUser() {
        User user = new User("user", "test@test.com", "raw-password");
        when(userDao.existsByEmail(user.getEmail())).thenReturn(false);
        when(userDao.existsByUsername(user.getRealUsername())).thenReturn(false);
        when(encoder.encode("raw-password")).thenReturn("enc");
        when(jwtService.generateAccessToken(anyString())).thenReturn("a");
        when(jwtService.generateRefreshToken(anyString())).thenReturn("r");

        AuthenticationResponse response = authService.register(user);

        verify(userDao).save(user);
        assertEquals("enc", user.getPassword());
        assertEquals("a", response.getAccessToken());
    }

    @Test
    void registerThrowsIfEmailExists() {
        User user = new User("user","e","p");
        when(userDao.existsByEmail("e")).thenReturn(true);
        assertThrows(ConflictException.class, () -> authService.register(user));
    }

    @Test
    void authenticateCallsAuthenticationManager() {
        User user = new User("u","e","p");
        when(jwtService.generateAccessToken(anyString())).thenReturn("a");
        when(jwtService.generateRefreshToken(anyString())).thenReturn("r");

        AuthenticationResponse response = authService.authenticate(user);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertEquals("a", response.getAccessToken());
    }

    @Test
    void refreshTokenReturnsUnauthorizedWhenNoUser() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        ResponseEntity<AuthenticationResponse> resp = authService.refreshToken(req);
        assertEquals(401, resp.getStatusCode().value());
    }

    @Test
    void extractUserFromRequestSuccess() {
        HttpServletRequest req = mock(HttpServletRequest.class);
        when(req.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token");
        when(jwtService.extractUsername("token")).thenReturn("e");
        User user = new User();
        when(userDao.findByEmail("e")).thenReturn(Optional.of(user));
        when(jwtService.isValid("token", user)).thenReturn(true);

        User result = authService.extractUserFromRequest(req);
        assertSame(user, result);
    }
}