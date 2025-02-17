package com.example.pk_pl.controller;

import com.example.pk_pl.dto.AuthenticationResponse;
import com.example.pk_pl.model.User;
import com.example.pk_pl.service.AuthService;
import com.example.pk_pl.service.DayOfWeekService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final DayOfWeekService dayOfWeekService;

    public AuthController(AuthService authService, DayOfWeekService dayOfWeekService) {
        this.authService = authService;
        this.dayOfWeekService = dayOfWeekService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user) {

        AuthenticationResponse authResponse = authService.register(user);

        dayOfWeekService.createDaysOfWeek(user);

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user){

        return ResponseEntity.ok(authService.authenticate(user));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthenticationResponse> refreshToken(HttpServletRequest request) {

        return authService.refreshToken(request);
    }
}
