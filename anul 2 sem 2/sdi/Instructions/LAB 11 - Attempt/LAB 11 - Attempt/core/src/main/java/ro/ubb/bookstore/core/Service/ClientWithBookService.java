package ro.ubb.bookstore.core.Service;

import ro.ubb.bookstore.core.Domain.*;
import ro.ubb.bookstore.core.Repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ClientWithBookService {
    private static Logger logger = LoggerFactory.getLogger(ClientWithBookService.class);
    @Autowired
    private Repository<Long, ClientWithBook> clientWithBookRepository;
    @Autowired
    private Repository<Long, Book> bookRepository;

    public List<ClientWithBook> getAllClientsWithBook() {
        List<ClientWithBook> clientWithBooks = this.clientWithBookRepository.findAll();
        clientWithBooks.forEach(c -> logger.info(c.toString()));
        return clientWithBooks;
    }

    public ClientWithBook saveClientWithBook(ClientWithBook clientWithBook) {
        Optional<Book> book = bookRepository.findById(clientWithBook.getCid());
        //todo
//        book.ifPresent(value -> ((BookRepository) bookRepository).updatePriceById(value.getId(), value.getPrice() - 1));
        ClientWithBook clientWithBook1 = clientWithBookRepository.save(clientWithBook);
        logger.info(clientWithBook1.toString());
        return clientWithBook1;
    }

    public ClientWithBook updateClientWithBook(ClientWithBook clientWithBook) {
        ClientWithBook updateClientWithBook = clientWithBookRepository.findById(clientWithBook.getId()).orElseThrow();
        updateClientWithBook.setCid(clientWithBook.getCid());
        updateClientWithBook.setBid(clientWithBook.getBid());
        logger.info(updateClientWithBook.toString());
        return clientWithBook;
    }

    public void returnBook(Long id) {
        Optional<ClientWithBook> clientWithBook = clientWithBookRepository.findById(id);
        if (clientWithBook.isPresent()) {
            Optional<Book> book = bookRepository.findById(clientWithBook.get().getCid());
            //todo
//            book.ifPresent(value -> ((BookRepository) bookRepository).updatePriceById(value.getId(), value.getPrice() + 1));
        }
    }

    public void deleteClientWithBook(Long id) {
        Optional<ClientWithBook> clientWithBook = clientWithBookRepository.findById(id);
        if (clientWithBook.isPresent()) {
            Optional<Book> book = bookRepository.findById(clientWithBook.get().getCid());
            clientWithBookRepository.deleteById(id);
            logger.info("deleted " + id);
            //todo
//            book.ifPresent(value -> ((BookRepository) bookRepository).updatePriceById(value.getId(), value.getPrice() + 1));
        }
    }
}
