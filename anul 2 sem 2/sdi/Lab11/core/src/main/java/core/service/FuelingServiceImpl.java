package core.service;

import core.converter.FuelingConverter;
import core.domain.Fueling;
import core.domain.CompositeKey;
import core.dtos.FuelingDto;
import core.repository.jpa.FuelingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FuelingServiceImpl implements FuelingService {
    @Autowired
    private FuelingRepo fuelingRepository;

    @Autowired
    private FuelingConverter fuelingConverter;

    @Override
    public FuelingDto save(FuelingDto fueling) {
        return fuelingConverter.convertModelToDto(
                fuelingRepository.save(new Fueling(fueling.getCarId(),
                        fueling.getGasStationId())));
    }

    @Override
    public void deleteById(Integer carId, Integer gasStationId) {
        fuelingRepository.deleteById(new CompositeKey(carId, gasStationId));
    }

    @Override
    public List<FuelingDto> findAll() {
        ArrayList<FuelingDto> result = new ArrayList<>();
        fuelingRepository.findAll().forEach((fueling -> {
            result.add(new FuelingDto(fueling.getArticleId(), fueling.getJournalistId()));
        }));

        return result;
    }
}