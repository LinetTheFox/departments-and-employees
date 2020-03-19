package exception;

/**
 * Exception caused when data in the database exists when it shouldn't or vice versa
 * @author linet
 */
public class DbValueException extends DatabaseException {
    public DbValueException(String message) {
        super(message);
    }
}
