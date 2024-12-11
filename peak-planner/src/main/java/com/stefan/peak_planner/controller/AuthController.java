package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.model.AuthenticationResponse;
import com.stefan.peak_planner.model.User;
import com.stefan.peak_planner.service.AuthService;
import com.stefan.peak_planner.service.DayOfWeekService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        ResponseEntity<AuthenticationResponse> response = ResponseEntity.ok(authService.register(user));

        dayOfWeekService.createDaysOfWeek(user);

        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user){

        return ResponseEntity.ok(authService.authenticate(user));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        return authService.refreshToken(request, response);
    }
}
