package core.service;

import core.converter.GasStationConverter;
import core.domain.GasStation;
import core.dtos.GasStationDto;
import core.dtos.GasStationsDto;
import core.exceptions.CarRentalsAppException;
import core.repository.jpa.GasStationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GasStationServiceImpl implements GasStationService {
    public static final Logger log = LoggerFactory.getLogger(GasStationServiceImpl.class);

    @Autowired
    private GasStationRepo gasStationRepository;

    @Autowired
    private GasStationConverter gasStationConverter;

    @Override
    public GasStationDto create(GasStationDto gasStation) {
        return gasStationConverter.convertModelToDto(gasStationRepository.save(new GasStation(
                gasStation.getId(), gasStation.getName())));
    }

    @Override
    public void deleteById(Integer id) {
        gasStationRepository.deleteById(id);
    }

    @Override
    public void update(GasStationDto gasStation) {
        gasStationRepository.findById(gasStation.getId()).orElseThrow(() ->
                new CarRentalsAppException("GasStation does not exist"));

        gasStationRepository.save(new GasStation(gasStation.getId(), gasStation.getName()));
    }

    @Override
    public GasStationDto findByName(String name) {

        var result = gasStationConverter.convertModelToDto(
                gasStationRepository.findByName(name));

        return result;
    }

    @Override
    public List<GasStationDto> findAll() {
        log.trace("entered findAll");
        System.out.println("ENTERED FIND ALL");
        ArrayList<GasStationDto> result = new ArrayList<>();
        gasStationRepository.findAll().forEach((gasStation -> {
            result.add(new GasStationDto(gasStation.getId(), gasStation.getName()));
        }));
        log.trace("findAll: {}", result);
        return result;
    }
}
