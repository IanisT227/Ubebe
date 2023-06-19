package ro.ubb.bookstore.core.Domain.exceptions;

public class ValidatorException extends RuntimeException {
    public ValidatorException(String msg) {
        super(msg);
    }
}
