package exception;

/**
 * Added for service so that servlets would know when user inputs invalid data.
 * @author linet
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
