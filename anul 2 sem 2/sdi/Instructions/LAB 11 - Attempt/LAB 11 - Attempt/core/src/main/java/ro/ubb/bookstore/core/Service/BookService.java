package ro.ubb.bookstore.core.Service;

import ro.ubb.bookstore.core.Domain.Book;
import ro.ubb.bookstore.core.Repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubb.bookstore.core.Repository.jpa.BookRepository;

import java.util.List;

@Service
public class BookService {
    private static Logger logger = LoggerFactory.getLogger(BookService.class);
    @Autowired
    private Repository<Long, Book> bookRepository;


    public Book saveBook(Book book) {
        Book book1 = bookRepository.save(book);
        logger.info(book1.toString());
        return book1;
    }

    public Book updateBook(Book book) {
        Book updateBook = bookRepository.findById(book.getId()).orElseThrow();
        updateBook.setTitle(book.getTitle());
        updateBook.setAuthor(book.getAuthor());
        updateBook.setYear(book.getYear());
        updateBook.setPrice(book.getPrice());
        updateBook.setPublishingHouseID(book.getPublishingHouseID());
        logger.info(updateBook.toString());
        return book;
    }

    public List<Book> getAllBooks() {
        List<Book> books = this.bookRepository.findAll();
        books.forEach(c -> logger.info(c.toString()));
        return books;
    }

    public List<Book> getBooksByTitle(String title) {
        List<Book> booksFiltered = (List<Book>) ((BookRepository) this.bookRepository).findAllByTitle(title);
        booksFiltered.forEach(m -> logger.info(m.toString()));
        return booksFiltered;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        logger.info("deleted " + id);
//        todo
//        ((ClientWithBookRepository) this.clientWithBookRepository).deleteClientWithBookByBid(id);

    }
}
