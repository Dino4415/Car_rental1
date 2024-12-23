package com.adpproject.Car_rental_spring.services.customer;

import com.adpproject.Car_rental_spring.dto.BookACarDto;
import com.adpproject.Car_rental_spring.dto.CarDto;
import com.adpproject.Car_rental_spring.entities.BookACar;
import com.adpproject.Car_rental_spring.entities.Car;
import com.adpproject.Car_rental_spring.entities.User;
import com.adpproject.Car_rental_spring.enums.BookCarStatus;
import com.adpproject.Car_rental_spring.repositories.BookACarRepository;
import com.adpproject.Car_rental_spring.repositories.CarRepository;
import com.adpproject.Car_rental_spring.repositories.UserRepositiory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository carRepository;

    private final UserRepositiory userRepository;

    private final BookACarRepository bookACarRepository;


    public CustomerServiceImpl(CarRepository carRepository, UserRepositiory userRepository, BookACarRepository bookACarRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.bookACarRepository = bookACarRepository;
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public boolean bookACar(Long carId, BookACarDto bookACarDto) {
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
        Optional<Car> optionalCar = carRepository.findById(carId);

        if(optionalUser.isPresent() && optionalCar.isPresent()) {
            BookACar bookACar = new BookACar();
            long diffInMilliSeconds=bookACarDto.getToDate().getTime()-bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
            bookACar.setDays(days);
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(optionalCar.get());
            bookACar.setAmount(optionalCar.get().getPrice().longValue()*days);
            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());
            bookACar.setBookCarStatus(BookCarStatus.PENDING);
            bookACarRepository.save(bookACar);
            return true;
        }
        return false;
    }

    @Override
    public List<BookACarDto> getBookingsByUserId(Long userId) {
        return bookACarRepository.findAllByuserId(userId).stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }
}
