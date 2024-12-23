package com.adpproject.Car_rental_spring.services.admin;

import com.adpproject.Car_rental_spring.dto.BookACarDto;
import com.adpproject.Car_rental_spring.dto.CarDto;
import com.adpproject.Car_rental_spring.entities.BookACar;
import com.adpproject.Car_rental_spring.entities.Car;
import com.adpproject.Car_rental_spring.enums.BookCarStatus;
import com.adpproject.Car_rental_spring.repositories.BookACarRepository;
import com.adpproject.Car_rental_spring.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AdminServiceImpl implements AdminService {

    private final CarRepository carRepository;

    private final BookACarRepository bookACarRepository;

    public AdminServiceImpl(CarRepository carRepository, BookACarRepository bookACarRepository) {
        this.carRepository = carRepository;
        this.bookACarRepository = bookACarRepository;
    }

    @Override
    public boolean postCar(CarDto carDto) {
        try {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice(carDto.getPrice());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setModelYear(carDto.getModelYear());
            car.setTransmission(carDto.getTransmission());
            car.setImage(carDto.getImage().getBytes());
            carRepository.save(car);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public boolean updateCar(Long carId, CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            existingCar.setName(carDto.getName());
            existingCar.setBrand(carDto.getBrand());
            existingCar.setColor(carDto.getColor());
            existingCar.setPrice(carDto.getPrice());
            existingCar.setType(carDto.getType());
            existingCar.setDescription(carDto.getDescription());
            existingCar.setModelYear(carDto.getModelYear());
            existingCar.setTransmission(carDto.getTransmission());
            if(carDto.getImage() != null) {
              existingCar.setImage(carDto.getImage().getBytes());
            }
            carRepository.save(existingCar);
            return true;

        }

        return false;
    }

    @Override
    public List<BookACarDto> getBookings() {
        return bookACarRepository.findAll().stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingid, String status) {
        Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingid);
        if (optionalBookACar.isPresent()) {
            BookACar existingBookACar = optionalBookACar.get();
            if(Objects.equals(status, "Approve")){
                existingBookACar.setBookCarStatus(BookCarStatus.APPROVED);
            }
            else{
                existingBookACar.setBookCarStatus(BookCarStatus.REJECTED);

            }
            bookACarRepository.save(existingBookACar);
            return true;
        }
        return false;
    }
}