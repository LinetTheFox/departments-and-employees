package dao;

import database.ConnectionFactory;
import domain.Department;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linet
 */
public class DepartmentDao implements Dao<Department> {

    private static Logger LOG = Logger.getLogger(DepartmentDao.class);

    @Override
    public Department get(long id) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM departments WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            Department department = new Department(rs.getString("name"));
            department.setId(rs.getLong("id"));

            connection.close();

            LOG.info("Successfully executed query: " + query);
            return department;

        } catch (SQLException e) {
            LOG.error("Failed to get a department.", e);
            try {
                connection.close();
            } catch (SQLException ex) {
                LOG.fatal("Failed to close the connection. ", ex);
            }
        }
        return null;
    }

    @Override
    public List<Department> getAll() {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "SELECT * FROM departments";
            PreparedStatement stmt = connection.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            List<Department> departments = new ArrayList<>();
            while (rs.next()) {
                Department department = new Department(rs.getString("name"));
                department.setId(rs.getLong("id"));
                departments.add(department);
            }
            connection.close();

            LOG.info("Successfully executed query: " + query);
            return departments;

        } catch (SQLException e) {
            LOG.error("Failed to get departments. ", e);
            try {
                connection.close();
            } catch (SQLException ex) {
                LOG.fatal("Failed to close the connection. ", ex);
            }
        }
        return null;
    }

    @Override
    public void save(Department department) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "INSERT INTO departments(name) VALUES(?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, department.getName());

            stmt.executeUpdate();
            connection.close();
            LOG.info("Successfully executed update: " + query);
        } catch (SQLException e) {
            LOG.error("Failed to save a department. ", e);
            try {
                connection.close();

            } catch (SQLException ex) {
                LOG.fatal("Failed to close the connection. ", ex);
            }
        }
    }

    @Override
    public void update(Long id, Department department) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "UPDATE departments SET name = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, department.getName());
            stmt.setLong(2, id);

            stmt.executeUpdate();
            connection.close();
            LOG.info("Successfully executed update: " + query);
        } catch (SQLException e) {
            LOG.error("Failed to update a department. ", e);
            try {
                connection.close();

            } catch (SQLException ex) {
                LOG.fatal("Failed to close the connection. ", ex);
            }
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            String query = "DELETE FROM departments WHERE id = ?";
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
                LOG.fatal("Failed to close the connection. ", ex);
            }
        }
    }
}
