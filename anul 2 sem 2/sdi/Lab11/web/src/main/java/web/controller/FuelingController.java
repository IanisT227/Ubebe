package web.controller;

import core.dtos.FuelingDto;
import core.dtos.FuelingsDto;
import core.service.FuelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FuelingController {
    @Autowired
    private FuelingService fuelingService;

    @RequestMapping(value="/fuelings")
    FuelingsDto getAllFuelings() {
        // TODO: logs

        return new FuelingsDto(fuelingService.findAll());
    }

    @RequestMapping(value="/fuelings", method = RequestMethod.POST)
    FuelingDto addFueling(@RequestBody FuelingDto fueling) {
        return fuelingService.save(fueling);
    }

    @RequestMapping(value="/fuelings/{carId}/{gasstationId}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteFueling(@PathVariable Integer carId, @PathVariable Integer gasstationId) {
        fuelingService.deleteById(carId, gasstationId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}