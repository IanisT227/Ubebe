package core.service;


import core.dtos.CarDto;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface CarService {
    CarDto create(CarDto car);
    
    void deleteById(Integer id);
    
    void update(CarDto car);

    List<CarDto> findAll();
}
