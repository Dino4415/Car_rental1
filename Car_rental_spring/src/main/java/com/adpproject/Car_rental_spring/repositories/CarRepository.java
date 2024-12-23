package com.adpproject.Car_rental_spring.repositories;

import com.adpproject.Car_rental_spring.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository

public interface CarRepository extends JpaRepository<Car, Long> {
}
