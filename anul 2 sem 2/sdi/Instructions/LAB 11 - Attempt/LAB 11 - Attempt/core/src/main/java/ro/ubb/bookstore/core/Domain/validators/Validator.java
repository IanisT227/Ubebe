package ro.ubb.bookstore.core.Domain.validators;

import ro.ubb.bookstore.core.Domain.exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
