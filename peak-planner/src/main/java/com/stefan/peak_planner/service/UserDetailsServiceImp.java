package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserDao userDao;

    public UserDetailsServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}