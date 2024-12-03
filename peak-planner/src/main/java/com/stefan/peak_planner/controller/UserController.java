package com.stefan.peak_planner.controller;

import com.stefan.peak_planner.entity.User;
import com.stefan.peak_planner.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    Service service;

    @Autowired
    public UserController(Service service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return service.register(user);
    }
}
