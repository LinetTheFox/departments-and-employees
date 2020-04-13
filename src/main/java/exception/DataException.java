package exception;

/**
 * Exception thrown when service couldn't retrieve data.
 * @author linet
 */
public class DataException extends Exception {
    public DataException(String message) {
        super(message);
    }
}
