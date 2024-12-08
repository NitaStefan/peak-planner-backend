package com.stefan.peak_planner.service;

import com.stefan.peak_planner.dao.UserDao;
import com.stefan.peak_planner.entity.User;
import com.stefan.peak_planner.entity.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);

        if(user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrincipal(user);
    }
}
