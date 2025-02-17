package com.example.pk_pl.service;

import com.example.pk_pl.dao.UserDao;
import com.example.pk_pl.dto.AuthenticationResponse;
import com.example.pk_pl.exception.ConflictException;
import com.example.pk_pl.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class AuthService {

    private final UserDao userDao;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder encoder;

    public AuthService(UserDao userDao, AuthenticationManager authenticationManager, JwtService jwtService, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    public AuthenticationResponse register(User user) {

        // trigger validation before encoding the password
        validateUser(user);

        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(User user) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        ); // throws BadCredentialsException if authentication fails

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {

        User user = extractUserFromRequest(request);

        if (user != null) {

            // generate access token
            String accessToken = jwtService.generateAccessToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            return new ResponseEntity<>(new AuthenticationResponse(accessToken, refreshToken), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private void validateUser(User user) {

        if (userDao.existsByEmail(user.getEmail())) {
            throw new ConflictException("The email address is already associated with an existing account");
        }

        if (userDao.existsByUsername(user.getRealUsername())) {
            throw new ConflictException("Username already exists");
        }

        // Validate user's constraints
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new ConstraintViolationException(errorMessage, violations);
        }
    }

    public User extractUserFromRequest(HttpServletRequest request) {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return null;

        String token = authHeader.substring(7);

        String email = jwtService.extractUsername(token);

        User user = userDao.findByEmail(email)
                .orElse(null);

        if (user == null || !jwtService.isValid(token, user))
            return null; // Invalid user or token

        return user;
    }
}
