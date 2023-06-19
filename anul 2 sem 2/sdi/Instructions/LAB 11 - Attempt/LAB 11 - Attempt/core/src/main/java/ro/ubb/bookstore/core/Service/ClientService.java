package ro.ubb.bookstore.core.Service;

import ro.ubb.bookstore.core.Domain.Client;
import ro.ubb.bookstore.core.Domain.ClientWithBook;
import ro.ubb.bookstore.core.Repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private static Logger logger = LoggerFactory.getLogger(ClientService.class);
    @Autowired
    private Repository<Long, Client> clientRepository;
    @Autowired
    private Repository<Long, ClientWithBook> clientWithBookRepository;

    public List<Client> getAllClients() {
        List<Client> clients = this.clientRepository.findAll();
        clients.forEach(c -> logger.info(c.toString()));
        return clients;
    }

    public Client saveClient(Client client) {
        Client client1 = clientRepository.save(client);
        logger.info(client1.toString());
        return client1;
    }

    public Client updateClient(Client client) {
        Client updateClient = clientRepository.findById(client.getId()).orElseThrow();
        updateClient.setName(client.getName());
        updateClient.setTotalBalance(client.getTotalBalance());
        logger.info(updateClient.toString());
        return client;
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
        logger.info("deleted " + id);
        //todo
//        ((RentalRepository) this.rentalRepository).deleteRentalsByCid(id);
    }

//    public String getDateFormatString() {
//        return this.formatString;
//    }
//
//    public void setDateFormatString(String newFormatString) {
//        this.formatString = newFormatString;
//        this.formatter = DateTimeFormatter.ofPattern(this.formatString);
//    }
}
