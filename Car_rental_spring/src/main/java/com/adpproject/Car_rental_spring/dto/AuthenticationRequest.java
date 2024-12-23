package com.adpproject.Car_rental_spring.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private String password;
}
