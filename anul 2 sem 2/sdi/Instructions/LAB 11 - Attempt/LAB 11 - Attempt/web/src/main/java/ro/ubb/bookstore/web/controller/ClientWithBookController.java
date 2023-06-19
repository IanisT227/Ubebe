package ro.ubb.bookstore.web.controller;

import ro.ubb.bookstore.core.Service.ClientWithBookService;
import ro.ubb.bookstore.web.converter.ClientWithBookConverter;
import ro.ubb.bookstore.web.dto.ClientWithBookDto;
import ro.ubb.bookstore.web.dto.ClientsWithBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientWithBookController {
    @Autowired
    private ClientWithBookService clientWithBookService;

    @Autowired
    private ClientWithBookConverter clientWithBookConverter;

//    @RequestMapping(value = "/students")
//    List<Student> getAllStudents() {
//        //todo: logs
//        return studentService.getAllStudents();
//
//    }

    @RequestMapping(value = "/clientswithbook")
    ClientsWithBookDto getAllClientsWithBook() {
        //todo: logs

        return new ClientsWithBookDto(
                clientWithBookConverter.convertModelsToDtos(
                        clientWithBookService.getAllClientsWithBook()));
    }

    @RequestMapping(value = "/clientswithbook", method = RequestMethod.POST)
    ClientWithBookDto addClientWithBook(@RequestBody ClientWithBookDto clientWithBookDto) {
        // TODO: Log parameters
        var clientWithBook = clientWithBookConverter.convertDtoToModel(clientWithBookDto);

        var result = clientWithBookService.saveClientWithBook(clientWithBook);

        var resultModel = clientWithBookConverter.convertModelToDto(result);

        // TODO: Log result model
        return resultModel;
    }

    @RequestMapping(value = "/clientswithbook/{id}", method = RequestMethod.PUT)
    ClientWithBookDto updateClientWithBook(@PathVariable Long id, @RequestBody ClientWithBookDto clientWithBookDto) {
        return
                clientWithBookConverter.convertModelToDto(
                        clientWithBookService.updateClientWithBook(
                                clientWithBookConverter.convertDtoToModel(clientWithBookDto)
                        ));
    }

    @RequestMapping(value = "/clientswithbook/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClientWithBook(@PathVariable Long id) {
        clientWithBookService.deleteClientWithBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
