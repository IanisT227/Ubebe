package ro.ubb.bookstore.core.Domain.exceptions;

public class NullException extends RuntimeException {
    public NullException(String msg) {
        super(msg);
    }
}

