package core.service;


import core.dtos.GasStationDto;
import core.dtos.GasStationsDto;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface GasStationService {
    GasStationDto create(GasStationDto gasStation);

    void deleteById(Integer id);

    void update(GasStationDto gasStation);

    GasStationDto findByName(String name);

    List<GasStationDto> findAll();
}
