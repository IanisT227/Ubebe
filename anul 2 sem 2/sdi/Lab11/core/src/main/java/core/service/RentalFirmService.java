package core.service;


import core.dtos.RentalFirmDto;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface RentalFirmService {
    RentalFirmDto create(RentalFirmDto car);

    void deleteById(Integer id);

    void update(RentalFirmDto car);

    List<RentalFirmDto> findAll();
}
