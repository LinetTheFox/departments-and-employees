package exception;

/**
 * Exception thrown when DbManager fails to initialize
 * @author linet
 */
public class DbInitializationException extends DatabaseException {
    public DbInitializationException(String message) {
        super(message);
    }
}
