package database;

import domain.Department;
import exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author linet
 */
public class DbManager {

    private static final String URL =
            "jdbc:h2:file:/home/linet/Projects/Java/departments-and-employees/data/dae";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DbManager() throws DatabaseException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS employees(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(200) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "salary INT NOT NULL," +
                    "department VARCHAR(100) NOT NULL);";
            stmt.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS departments(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(200) NOT NULL);";
            stmt.executeUpdate(query);

            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Database error.");
        }
    }

    public boolean addDepartment(Department department) {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();

            String query = String.format("INSERT INTO DEPARTMENTS(name) VALUES ( %s )",
                    department.getName());

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
