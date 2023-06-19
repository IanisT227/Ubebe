package core.exceptions;

/**
 * Base Exception class
 *
 * @author Octa
 */
public class CarRentalsAppException extends RuntimeException {

    public CarRentalsAppException(String message) {
        super(message);
    }

    public CarRentalsAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarRentalsAppException(Throwable cause) {
        super(cause);
    }
}