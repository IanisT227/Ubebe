package ro.ubb.bookstore.web.controller;

import ro.ubb.bookstore.core.Service.PublishingHouseService;
import ro.ubb.bookstore.web.converter.PublishingHouseConverter;
import ro.ubb.bookstore.web.dto.PublishingHouseDto;
import ro.ubb.bookstore.web.dto.PublishingHousesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PublishingHouseController {
    private static Logger logger = LoggerFactory.getLogger(PublishingHouseController.class);
    @Autowired
    private PublishingHouseService publishingHouseService;

    @Autowired
    private PublishingHouseConverter publishingHouseConverter;

//    @RequestMapping(value = "/students")
//    List<Student> getAllStudents() {
//        //todo: logs
//        return studentService.getAllStudents();
//
//    }

    @RequestMapping(value = "/publishinghouses" +
            "")
    PublishingHousesDto getAllPublishingHouses() {
        //todo: logs
        logger.info("get all");
        return new PublishingHousesDto(
                publishingHouseConverter.convertModelsToDtos(
                        publishingHouseService.getPublishingHouses()));
    }

    @RequestMapping(value = "/publishinghouses" +
            "Sorted")
    PublishingHousesDto getPublishingHousesSorted() {
        //todo: logs
        logger.info("get sorted");
        return new PublishingHousesDto(
                publishingHouseConverter.convertModelsToDtos(
                        publishingHouseService.getSorted()));
    }

    @RequestMapping(value = "/publishinghouses" +
            "", method = RequestMethod.POST)
    PublishingHouseDto addPublishingHouse(@RequestBody PublishingHouseDto publishingHouseDto) {
        // TODO: Log parameters
        logger.info("add " + publishingHouseDto.toString());
        var publishingHouse = publishingHouseConverter.convertDtoToModel(publishingHouseDto);

        var result = publishingHouseService.savePublishingHouse(publishingHouse);

        var resultModel = publishingHouseConverter.convertModelToDto(result);

        // TODO: Log result model
        logger.info("add " + resultModel.toString());
        return resultModel;
    }

    @RequestMapping(value = "/publishinghouses" +
            "/{id}", method = RequestMethod.PUT)
    PublishingHouseDto updatePublishingHouse(@PathVariable Long id, @RequestBody PublishingHouseDto publishingHouseDto) {
        publishingHouseDto.setId(id);
        return
                publishingHouseConverter.convertModelToDto(
                        publishingHouseService.updatePublishingHouse(
                                publishingHouseConverter.convertDtoToModel(publishingHouseDto)
                        ));
    }

    @RequestMapping(value = "/publishinghouses" +
            "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deletePublishingHouse(@PathVariable Long id) {
        publishingHouseService.deletePublishingHouse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
