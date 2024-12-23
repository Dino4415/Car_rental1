package com.adpproject.Car_rental_spring.dto;

import com.adpproject.Car_rental_spring.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public long getUserId() {
        return userId;
    }

    private String jwt;

    private UserRole userRole;

    private long userId;
}
