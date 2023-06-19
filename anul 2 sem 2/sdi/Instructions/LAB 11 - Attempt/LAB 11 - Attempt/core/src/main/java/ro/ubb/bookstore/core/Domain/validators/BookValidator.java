package ro.ubb.bookstore.core.Domain.validators;


import ro.ubb.bookstore.core.Domain.Book;
import ro.ubb.bookstore.core.Domain.exceptions.ValidatorException;

import java.io.Serializable;

public class BookValidator implements Validator<Book>, Serializable {

    private static final int CURRENT_YEAR = 2022;

    @Override
    public void validate(Book entity) throws ValidatorException {
        if (entity.getAuthor().split(" ").length < 2)
            throw new ValidatorException("Author name must have at least 2 words");
        if (entity.getPrice() < 0)
            throw new ValidatorException("Boo price cannot be negative");
        if (entity.getTitle().isEmpty())
            throw new ValidatorException("Book title cannot be empty");
        if (entity.getYear() > CURRENT_YEAR)
            throw new ValidatorException("Book year cannot exceed " + CURRENT_YEAR);
    }
}
