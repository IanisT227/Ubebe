package core.service;

import core.dtos.FuelingDto;

import java.util.List;

public interface FuelingService {
    FuelingDto save(FuelingDto fueling);

    void deleteById(Integer carId, Integer gasStationId);

    List<FuelingDto> findAll();
}
