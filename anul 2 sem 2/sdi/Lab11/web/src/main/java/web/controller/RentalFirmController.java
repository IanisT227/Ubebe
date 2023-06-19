package web.controller;

import core.dtos.RentalFirmDto;
import core.dtos.RentalFirmsDto;
import core.service.RentalFirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RentalFirmController {
    @Autowired
    private RentalFirmService rentalfirmService;

    @RequestMapping(value="/rentalfirms")
    RentalFirmsDto getAllRentalFirms() {
        // TODO: logs

        return new RentalFirmsDto(rentalfirmService.findAll());
    }

    @RequestMapping(value="/rentalfirms", method = RequestMethod.POST)
    RentalFirmDto addRentalFirm(@RequestBody RentalFirmDto rentalfirm) {
        return rentalfirmService.create(rentalfirm);
    }

    @RequestMapping(value="/rentalfirms", method = RequestMethod.PUT)
    ResponseEntity<?> updateRentalFirm(@RequestBody RentalFirmDto rentalfirm) {
        rentalfirmService.update(rentalfirm);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/rentalfirms/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRentalFirm(@PathVariable Integer id) {
        rentalfirmService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
