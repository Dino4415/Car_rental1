package com.adpproject.Car_rental_spring.services;

import com.adpproject.Car_rental_spring.dto.SignupRequest;
import com.adpproject.Car_rental_spring.dto.UserDto;
import com.adpproject.Car_rental_spring.entities.User;
import com.adpproject.Car_rental_spring.enums.UserRole;
import com.adpproject.Car_rental_spring.repositories.UserRepositiory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service


public class AuthServiceImpl implements AuthService {



    private final UserRepositiory userRepositiory;
    @PostConstruct
    public void createAdminAccount() {
        User adminAccount=userRepositiory.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User newAdminAccount=new User();
            newAdminAccount.setName("admin");
            newAdminAccount.setUserRole(UserRole.ADMIN);
            newAdminAccount.setEmail("admin@test.com");
            newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepositiory.save(newAdminAccount);
        }
    }

    public AuthServiceImpl(UserRepositiory userRepositiory) {
        this.userRepositiory = userRepositiory;
    }
    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdCustomer=userRepositiory.save(user);
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(createdCustomer.getId());
        createdUserDto.setName(createdCustomer.getName());
        createdUserDto.setEmail(createdCustomer.getEmail());
        createdUserDto.setUserRole(createdCustomer.getUserRole());
        return createdUserDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepositiory.findFirstByEmail(email).isPresent();
    }
}
