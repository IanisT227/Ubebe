package core.service;

import core.converter.CarConverter;
import core.domain.Car;
import core.dtos.CarDto;
import core.exceptions.CarRentalsAppException;
import core.repository.jpa.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepo carRepository;

    @Autowired
    private CarConverter carConverter;

    @Override
    public CarDto create(CarDto car) {
        return carConverter.convertModelToDto(carRepository.save(new Car(
                car.getId(), car.getBrand(), car.getRentalfirmId())));
    }

    @Override
    public void deleteById(Integer id) {
        carRepository.deleteById(id);
    }

    @Override
    public void update(CarDto car) {
        carRepository.findById(car.getId()).orElseThrow(() ->
                new CarRentalsAppException("Car does not exist"));

        carRepository.save(new Car(car.getId(), car.getBrand(), car.getRentalfirmId()));
    }

    @Override
    public List<CarDto> findAll() {
        ArrayList<CarDto> result = new ArrayList<>();
        carRepository.findAll().forEach((car -> {
            result.add(new CarDto(car.getId(), car.getBrand(), car.getRentalfirmId()));
        }));

        return result;
    }
}
