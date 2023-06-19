package core.converter;

import core.domain.Car;
import core.dtos.CarDto;
import org.springframework.stereotype.Component;

@Component
public class CarConverter extends BaseConverter<Car, CarDto> {
    @Override
    public Car convertDtoToModel(CarDto dto) {
        return new Car(dto.getId(),  dto.getBrand(), dto.getRentalfirmId());
    }

    @Override
    public CarDto convertModelToDto(Car Car) {
        CarDto dto = new CarDto(Car.getId(), Car.getBrand(), Car.getRentalfirmId());
        dto.setId(Car.getId());
        return dto;
    }
}
