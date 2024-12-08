package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.UserDao;
import com.stefan.peak_planner.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.stereotype.Service
public class Service {

    private final UserDao userDao;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    public Service(UserDao userDao, AuthenticationManager authenticationManager, JWTService jwtService) {

        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    public String verify(User user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated())
            return "Success"; // generateToken() method

        return "Fail"; // actually returns 401 Unauthorized
    }
}
