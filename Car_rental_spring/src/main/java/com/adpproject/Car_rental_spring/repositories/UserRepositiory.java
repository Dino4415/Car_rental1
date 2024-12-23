package com.adpproject.Car_rental_spring.repositories;

import com.adpproject.Car_rental_spring.entities.User;
import com.adpproject.Car_rental_spring.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepositiory extends JpaRepository<User, Long> {

    Optional<User> findFirstByEmail(String email);
    User findByUserRole(UserRole userRole);


}
