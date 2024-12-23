package com.adpproject.Car_rental_spring.services.customer;

import com.adpproject.Car_rental_spring.dto.BookACarDto;
import com.adpproject.Car_rental_spring.dto.CarDto;

import java.util.List;

public interface CustomerService {
    List<CarDto> getAllCars();

    CarDto getCarById(Long carId);

    boolean bookACar(Long carId, BookACarDto bookACarDto);

    List<BookACarDto> getBookingsByUserId(Long userId);
}
