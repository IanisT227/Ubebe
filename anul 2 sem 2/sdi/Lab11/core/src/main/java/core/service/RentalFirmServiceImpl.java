package core.service;

import core.converter.RentalFirmConverter;
import core.domain.RentalFirm;
import core.dtos.RentalFirmDto;
import core.exceptions.CarRentalsAppException;
import core.repository.jpa.RentalFirmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentalFirmServiceImpl implements RentalFirmService {
    @Autowired
    private RentalFirmRepo carRepository;

    @Autowired
    private RentalFirmConverter carConverter;

    @Override
    public RentalFirmDto create(RentalFirmDto car) {
        return carConverter.convertModelToDto(carRepository.save(new RentalFirm(
                car.getId(), car.getName())));
    }

    @Override
    public void deleteById(Integer id) {
        carRepository.deleteById(id);
    }

    @Override
    public void update(RentalFirmDto car) {
        carRepository.findById(car.getId()).orElseThrow(() ->
                new CarRentalsAppException("RentalFirm does not exist"));

        carRepository.save(new RentalFirm(car.getId(), car.getName()));
    }

    @Override
    public List<RentalFirmDto> findAll() {
        ArrayList<RentalFirmDto> result = new ArrayList<>();
        carRepository.findAll().forEach((car -> {
            result.add(new RentalFirmDto(car.getId(), car.getName()));
        }));

        return result;
    }
}
