package exception;

/**
 * Thrown when invalid data is passed to a service.
 * @author linet
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
