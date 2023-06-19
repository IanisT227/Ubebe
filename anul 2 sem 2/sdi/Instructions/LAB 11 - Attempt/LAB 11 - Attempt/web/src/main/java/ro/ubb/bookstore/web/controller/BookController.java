package ro.ubb.bookstore.web.controller;

import ro.ubb.bookstore.core.Service.BookService;
import ro.ubb.bookstore.web.converter.BookConverter;
import ro.ubb.bookstore.web.dto.BookDto;
import ro.ubb.bookstore.web.dto.BooksDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    //    @RequestMapping(value = "/students")
//    List<Student> getAllStudents() {
//        //todo: logs
//        return studentService.getAllStudents();
//
//    }
    @RequestMapping(value = "/books/{title}")
    BooksDto getAllBooksByTitle(@PathVariable String title) {
        //todo: logs

        return new BooksDto(
                bookConverter.convertModelsToDtos(
                        bookService.getBooksByTitle(title)));
    }

    @RequestMapping(value = "/books")
    BooksDto getAllBooks() {
        //todo: logs

        return new BooksDto(
                bookConverter.convertModelsToDtos(
                        bookService.getAllBooks()));
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    BookDto addBook(@RequestBody BookDto bookDto) {
        // TODO: Log parameters
        var book = bookConverter.convertDtoToModel(bookDto);

        var result = bookService.saveBook(book);

        var resultModel = bookConverter.convertModelToDto(result);

        // TODO: Log result model
        return resultModel;
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        return
                bookConverter.convertModelToDto(
                        bookService.updateBook(
                                bookConverter.convertDtoToModel(bookDto)
                        ));
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
