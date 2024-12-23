package com.adpproject.Car_rental_spring.controllers;


import com.adpproject.Car_rental_spring.dto.AuthenticationRequest;
import com.adpproject.Car_rental_spring.dto.AuthenticationResponse;
import com.adpproject.Car_rental_spring.dto.SignupRequest;
import com.adpproject.Car_rental_spring.dto.UserDto;
import com.adpproject.Car_rental_spring.entities.User;
import com.adpproject.Car_rental_spring.repositories.UserRepositiory;
import com.adpproject.Car_rental_spring.services.AuthService;
import com.adpproject.Car_rental_spring.services.jwt.UserService;
import com.adpproject.Car_rental_spring.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, AuthService authService, UserRepositiory userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.userRepository = userRepository;
    }

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    private final UserRepositiory userRepository;



    @PostMapping("/signup")
    public ResponseEntity<?> createCustomer(@RequestBody SignupRequest signupRequest) {
        if(authService.hasCustomerWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already exist.");
        }
        UserDto createdUserDto=authService.createCustomer(signupRequest);
        if(createdUserDto==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);

    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest)
            throws BadCredentialsException, DisabledException, UsernameNotFoundException {

        try {
            // Authenticate the user using the provided username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.", e);
        }

        // Load user details using the username
        final UserDetails userDetails = userService
                .userDetailsService()
                .loadUserByUsername(authenticationRequest.getEmail());

        // Find the user entity using the email/username
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());


        // Generate the JWT token for the user
        final String jwt = jwtUtil.generateToken(userDetails);

        // Build the authentication response
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
    }


}
