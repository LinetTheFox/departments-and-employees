package database;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory class that creates an instance Connection for using in several Dao classes.
 * In this case - in DepartmentDao and EmployeeDao.
 * @author linet
 */
public class ConnectionFactory {

    private static Logger LOG = Logger.getLogger(ConnectionFactory.class);

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOG.error("Could not find the JDBC Driver class. Stack trace as follows: ", e);
        }
    }

    /**
     * URL to the database.
     */
    public static final String URL = "jdbc:h2:file:/home/linet/Projects/Java/departments-and-employees/data/dae";

    /**
     * Method that returns Connection instance.
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch(SQLException e) {
            LOG.error("Could not connect to database");
            throw new RuntimeException("Could not connect to database");
        }
    }
}
