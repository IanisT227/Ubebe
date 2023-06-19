package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.Domain.Book;
import ro.ubb.bookstore.core.Domain.Book;
import ro.ubb.bookstore.web.dto.BookDto;
import ro.ubb.bookstore.web.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookConverter extends BaseConverter<Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {
        var model = new Book();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setAuthor(dto.getAuthor());
        model.setYear(dto.getYear());
        model.setPrice(dto.getPrice());
        model.setPublishingHouseID(dto.getPublishingHouseID());
        return model;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto dto = new BookDto(book.getTitle(), book.getAuthor(), book.getYear(), book.getPrice(), book.getPublishingHouseID());
        dto.setId(book.getId());
        return dto;
    }
}
