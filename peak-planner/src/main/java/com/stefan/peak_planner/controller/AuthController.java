package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.AuthenticationResponse;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.service.AuthService;
import com.stefan.peak_planner.service.DayOfWeekService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public ResponseEntity refreshToken(HttpServletRequest request) {

        return authService.refreshToken(request);
    }
}
