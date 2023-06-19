package ro.ubb.bookstore.web.controller;

import ro.ubb.bookstore.core.Service.ClientService;
import ro.ubb.bookstore.web.converter.ClientConverter;
import ro.ubb.bookstore.web.dto.ClientDto;
import ro.ubb.bookstore.web.dto.ClientsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientConverter clientConverter;

//    @RequestMapping(value = "/students")
//    List<Student> getAllStudents() {
//        //todo: logs
//        return studentService.getAllStudents();
//
//    }

    @RequestMapping(value = "/clients")
    ClientsDto getAllClients() {
        //todo: logs

        return new ClientsDto(
                clientConverter.convertModelsToDtos(
                        clientService.getAllClients()));
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto addClient(@RequestBody ClientDto clientDto) {
        // TODO: Log parameters
        var client = clientConverter.convertDtoToModel(clientDto);

        var result = clientService.saveClient(client);

        var resultModel = clientConverter.convertModelToDto(result);

        // TODO: Log result model
        return resultModel;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        clientDto.setId(id);
        return
                clientConverter.convertModelToDto(
                        clientService.updateClient(
                                clientConverter.convertDtoToModel(clientDto)
                        ));
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
