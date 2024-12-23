package com.adpproject.Car_rental_spring.services;

import com.adpproject.Car_rental_spring.dto.SignupRequest;
import com.adpproject.Car_rental_spring.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);
    boolean hasCustomerWithEmail(String email);
}
