package com.adpproject.Car_rental_spring.services.jwt;

import com.adpproject.Car_rental_spring.repositories.UserRepositiory;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserServiceImpl implements UserService {
    public UserServiceImpl(UserRepositiory userRepositiory) {
        this.userRepositiory = userRepositiory;
    }

    private final UserRepositiory userRepositiory;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepositiory.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
