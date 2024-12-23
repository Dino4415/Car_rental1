package com.adpproject.Car_rental_spring.controllers;

import com.adpproject.Car_rental_spring.dto.BookACarDto;
import com.adpproject.Car_rental_spring.dto.CarDto;
import com.adpproject.Car_rental_spring.services.customer.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars(){
        List<CarDto> carDtoList =customerService.getAllCars();
        return ResponseEntity.ok(carDtoList);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long carId){
        CarDto carDto = customerService.getCarById(carId);
        if(carDto != null) {
            return ResponseEntity.ok(carDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/car/book/{carId}")
    public ResponseEntity<?> bookACar(@PathVariable Long carId, @RequestBody BookACarDto bookACarDto){
        boolean success=customerService.bookACar(carId, bookACarDto);
        if(success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/bookings/{userId}")
    public ResponseEntity<?> getBookingsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBookingsByUserId(userId));
    }
}
