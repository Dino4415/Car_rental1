package com.adpproject.Car_rental_spring.repositories;

import com.adpproject.Car_rental_spring.dto.BookACarDto;
import com.adpproject.Car_rental_spring.entities.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookACarRepository extends JpaRepository<BookACar, Long> {

    List<BookACar> findAllByuserId(Long userId);
}
