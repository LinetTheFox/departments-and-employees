package exception;

/**
 * General exception for database part.
 * @author linet
 */
public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}
