package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.UserDao;
import com.stefan.peak_planner.model.AuthenticationResponse;
import com.stefan.peak_planner.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(user);

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public AuthenticationResponse authenticate(User user) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {

            String accessToken = jwtService.generateAccessToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            return new AuthenticationResponse(accessToken, refreshToken);
        }

        throw new BadCredentialsException("Invalid username or password");
    }
}