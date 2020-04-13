package dao;

import database.ConnectionFactory;
import domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dao class for {@link Employee}
 * @author linet
 */
public class EmployeeDao implements Dao<Employee> {

    private static Logger LOG = LoggerFactory.getLogger(EmployeeDao.class);

    @Override
    public Employee get(long id) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM employees WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            Employee employee = new Employee(
                    rs.getString("name"),
                    rs.getDate("hire_date"),
                    rs.getString("email"),
                    rs.getInt("salary"),
                    rs.getString("dep_name")
            );
            employee.setId(rs.getLong("id"));

            connection.close();

            LOG.info("Successfully executed query: " + query);
            return employee;

        } catch (SQLException e) {
            LOG.error("Failed to get an employee.", e);
            try {
                connection.close();
            } catch (SQLException ex) {
                LOG.error("Failed to close the connection. ", ex);
            }
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM employees";
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getString("name"),
                        rs.getDate("hire_date"),
                        rs.getString("email"),
                        rs.getInt("salary"),
                        rs.getString("dep_name")
                );
                employee.setId(rs.getLong("id"));
                employees.add(employee);
            }
            connection.close();

            LOG.info("Successfully executed query: " + query);
            return employees;

        } catch (SQLException e) {
            LOG.error("Failed to get employees. ", e);
            try {
                connection.close();
            } catch (SQLException ex) {
                LOG.error("Failed to close the connection. ", ex);
            }
        }
        return null;
    }

    @Override
    public void save(Employee employee) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO employees(name, hire_date, email, salary, dep_name) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, employee.getName());
            stmt.setDate(2, new Date(employee.getDate().getTime()));
            stmt.setString(3, employee.getEmail());
            stmt.setInt(4, employee.getSalary());
            stmt.setString(5, employee.getDepartmentName());

            stmt.executeUpdate();
            connection.close();
            LOG.info("Successfully executed update: " + query);
        } catch (SQLException e) {
            LOG.error("Failed to save an employee. ", e);
            try {
                connection.close();

            } catch (SQLException ex) {
                LOG.error("Failed to close the connection. ", ex);
            }
        }
    }

    @Override
    public void update(Long id, Employee employee) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE employees SET name = ?, email = ?, hire_date = ?, salary = ?, dep_name = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setDate(3, new Date(employee.getDate().getTime()));
            stmt.setInt(4, employee.getSalary());
            stmt.setString(5, employee.getDepartmentName());
            stmt.setLong(6, id);

            stmt.executeUpdate();
            connection.close();
            LOG.info("Successfully executed update: " + query);
        } catch (SQLException e) {
            LOG.error("Failed to update an employee. ", e);
            try {
                connection.close();

            } catch (SQLException ex) {
                LOG.error("Failed to close the connection. ", ex);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "DELETE FROM employees WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            stmt.executeUpdate();
            connection.close();
            LOG.info("Successfully executed update: " + query);
        } catch (SQLException e) {
            LOG.error("Failed to delete a department. ", e);
            try {
                connection.close();

            } catch (SQLException ex) {
                LOG.error("Failed to close the connection. ", ex);
            }
        }
    }
}
