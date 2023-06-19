package web.controller;

import core.dtos.CarDto;
import core.dtos.CarsDto;
import core.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping(value="/cars")
    CarsDto getAllCars() {
        // TODO: logs

        return new CarsDto(carService.findAll());
    }

    @RequestMapping(value="/cars", method = RequestMethod.POST)
    CarDto addCar(@RequestBody CarDto car) {
        return carService.create(car);
    }

    @RequestMapping(value="/cars", method = RequestMethod.PUT)
    ResponseEntity<?> updateCar(@RequestBody CarDto car) {
        carService.update(car);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/cars/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        carService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
