package com.adpproject.Car_rental_spring.services.admin;

import com.adpproject.Car_rental_spring.dto.BookACarDto;
import com.adpproject.Car_rental_spring.dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(CarDto carDto);

    List<CarDto> getAllCars();

    void deleteCar(Long id);

    CarDto getCarById(Long carId);

    boolean updateCar(Long carId,CarDto carDto) throws IOException;

    List<BookACarDto> getBookings();

    boolean changeBookingStatus(Long bookingid,String status);


}
