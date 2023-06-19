package web.controller;

import core.dtos.GasStationDto;
import core.dtos.GasStationsDto;
import core.service.GasStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class GasStationController {
    @Autowired
    private GasStationService gasstationService;

    private static final Logger log = LoggerFactory.getLogger(GasStationController.class);

    @RequestMapping(value="/gasstations")
    GasStationsDto getAllGasStations() {
        log.trace("ENTERED GET ALL");

        GasStationsDto dto = new GasStationsDto(gasstationService.findAll());
//        log.trace("getAllArticles: {}", dto);
        return dto;
    }

    @RequestMapping(value="/gasstations", method = RequestMethod.POST)
    GasStationDto addGasStation(@RequestBody GasStationDto gasstation) {
        log.trace("entered add gas station");
        return gasstationService.create(gasstation);
    }

    @RequestMapping(value="/gasstations", method = RequestMethod.PUT)
    ResponseEntity<?> updateGasStation(@RequestBody GasStationDto gasstation) {
        log.trace("entered update");
        gasstationService.update(gasstation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/gasstations/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteGasStation(@PathVariable Integer id) {
        log.trace("entered delete gas station");
        gasstationService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/gasstations/find_by_name/{name}", method = RequestMethod.GET)
    GasStationDto findByName(@PathVariable String name) {
        log.trace("entered findByName with name = {}", name);

        var result = gasstationService.findByName(name);

        log.trace("found category {}", result);
        return result;
    }
}
