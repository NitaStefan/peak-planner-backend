package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.UserDao;
import com.stefan.peak_planner.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.stereotype.Service
public class Service {

    UserDao userDao;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    public Service(UserDao userDao) {
        this.userDao = userDao;
    }

    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userDao.save(user);
    }
}
