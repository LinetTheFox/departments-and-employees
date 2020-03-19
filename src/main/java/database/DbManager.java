package database;

import domain.Employee;
import exception.DatabaseException;
import exception.DbValueException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that responds for main operations on database: adding/editing/removing departments
 * and employees and listing the employees.
 * @author linet
 */
public class DbManager {

    /**
     * The URL used to connect to the database, depending on the db may also pass login and
     * password as url parameters (e. g. jdbc:dbtype:protocol:/url?login=ABC&password=ABCD).
     */
    private static final String URL =
            "jdbc:h2:file:/home/linet/Projects/Java/departments-and-employees/data/dae";

    /**
     * Static block that loads database driver when the app begins (more exactly - when
     * this class is loaded).
     */
    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor creates two tables - departments and employees (if such don't exist yet).
     * @throws DatabaseException when the initialization has failed - mostly if the database
     * connection is already busy.
     */
    public DbManager() throws DatabaseException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS employees(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "emp_name VARCHAR(200) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "salary INT NOT NULL," +
                    "department VARCHAR(100) NOT NULL," +
                    "hire_date DATE NOT NULL);";
            stmt.executeUpdate(query);

            query = "CREATE TABLE IF NOT EXISTS departments(" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "dep_name VARCHAR(200) NOT NULL UNIQUE);";
            stmt.executeUpdate(query);
            connection.commit();

            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Couldn't initialize database.");
        }
    }

    /**
     * Creates a new department with given name
     * @param name name of the department
     * @return whether the operation was successful
     * @throws DbValueException if department with such name already exists
     */
    public boolean addDepartment(String name) throws DbValueException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();
            String checkQuery = String.format("SELECT dep_name FROM departments WHERE dep_name = '%s'", name);

            ResultSet rs = stmt.executeQuery(checkQuery);
            if (rs.next()) {
                throw new DbValueException("This department already exists!");
            }
            stmt.close();

            PreparedStatement prStmt = connection.prepareStatement("INSERT INTO departments(dep_name) VALUES (?)");
            prStmt.setString(1, name);
            prStmt.executeUpdate();

            connection.commit();
            prStmt.close();
            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Removes a department
     * @param name name of the department
     * @return whether the operation was successful
     * @throws DbValueException if such department doesn't exist
     */
    public boolean removeDepartment(String name) throws DbValueException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();
            String checkQuery = String.format("SELECT dep_name FROM departments WHERE dep_name = '%s'", name);

            ResultSet rs = stmt.executeQuery(checkQuery);
            if (!rs.next()) {
                throw new DbValueException("This department doesn't exist.");
            }
            stmt.close();

            PreparedStatement prStmt = connection.prepareStatement("DELETE FROM departments WHERE dep_name = ?");
            prStmt.setString(1, name);
            prStmt.executeUpdate();

            connection.commit();
            prStmt.close();
            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Edits the name of the department and the 'department' columns of all employees that are
     * in that department.
     * @param name old name of the department
     * @param newName new name of the department
     * @return whether the operation was successful
     * @throws DbValueException if such department doesn't exist
     */
    public boolean editDepartment(String name, String newName) throws DbValueException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();
            String checkQuery = String.format("SELECT dep_name FROM departments WHERE dep_name = '%s'", name);

            ResultSet rs = stmt.executeQuery(checkQuery);
            if (!rs.next()) {
                throw new DbValueException("This department doesn't exist.");
            }
            stmt.close();

            PreparedStatement prStmt = connection.prepareStatement("UPDATE departments SET dep_name = ? WHERE dep_name = ?");
            prStmt.setString(1, newName);
            prStmt.setString(2, name);
            prStmt.executeUpdate();

            PreparedStatement empPrStmt = connection.prepareStatement("UPDATE employees SET department = ? WHERE department = ?");
            empPrStmt.setString(1, newName);
            empPrStmt.setString(2, name);
            empPrStmt.executeUpdate();

            connection.commit();
            prStmt.close();
            empPrStmt.close();
            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lists all employees of given department.
     * @param depName name of the department
     * @return {@link List} of {@link Employee} objects representing the employees from this department.
     */
    public List<Employee> listEmployees(String depName) {

        try {
            Connection connection = DriverManager.getConnection(URL);

            PreparedStatement prStmt = connection.prepareStatement("SELECT * FROM employees WHERE department=?");
            prStmt.setString(1, depName);
            ResultSet rs = prStmt.executeQuery();

            List<Employee> list = new ArrayList<>();

            while(rs.next()) {
                String name = rs.getString("emp_name");
                Date date = rs.getDate("hire_date");
                String email = rs.getString("email");
                Integer salary = rs.getInt("salary");
                String departmentName = rs.getString("department");

                list.add(new Employee(name, date, email, salary, departmentName));
            }
            prStmt.close();
            connection.close();

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new employee
     * @param employee {@link Employee} object containing their information.
     * @return whether the operation was successful
     * @throws DbValueException if employee with such email already exists or the specified department doesn't
     */
    public boolean addEmployee(Employee employee) throws DbValueException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();

            String checkDept = String.format("SELECT dep_name FROM departments WHERE dep_name = '%s'",
                    employee.getDepartmentName());
            ResultSet rs = stmt.executeQuery(checkDept);
            if (!rs.next()) {
                throw new DbValueException("This department doesn't exist!");
            }

            String checkEmp = String.format("SELECT email FROM employees WHERE email = '%s'", employee.getEmail());
            rs = stmt.executeQuery(checkEmp);
            if(rs.next()) {
                throw new DbValueException("An employee with this email already exists!");
            }
            stmt.close();

            Date date = new Date(employee.getDate().getTime());
            PreparedStatement prStmt = connection.prepareStatement("INSERT INTO employees(emp_name, email, salary, department, hire_date)" +
                    "VALUES(?, ?, ?, ?, ?)");
            prStmt.setString(1, employee.getName());
            prStmt.setString(2, employee.getEmail());
            prStmt.setInt(3, employee.getSalary());
            prStmt.setString(4, employee.getDepartmentName());
            prStmt.setDate(5, date);
            prStmt.executeUpdate();

            connection.commit();
            prStmt.close();
            connection.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Edits the employee information.
     * Note: the employee is searched by email since the task specifies that it is unique and is
     * presumably persistent, so it is used as a form of an ID here.
     * @param newEmployee {@link Employee} object containing new Employee info
     * @param email email of the employee
     * @return whether the operation was successful
     * @throws DbValueException if such employee or their new department don't exist
     */
    public boolean editEmployee(Employee newEmployee, String email) throws DbValueException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();

            String checkEmp = String.format("SELECT emp_name FROM employees WHERE email = '%s'", email);
            ResultSet rs = stmt.executeQuery(checkEmp);
            if (!rs.next()) {
                throw new DbValueException("This employee doesn't exist!");
            }

            String checkDpt = String.format("SELECT dep_name FROM departments WHERE dep_name = '%s'",
                    newEmployee.getDepartmentName());
            rs = stmt.executeQuery(checkDpt);
            if (!rs.next()) {
                throw new DbValueException("This department doesn't exist!");
            }
            stmt.close();

            PreparedStatement prStmt = connection.prepareStatement("UPDATE employees " +
                    "SET emp_name = ?, salary = ?, department = ? WHERE email = ?");
            prStmt.setString(1, newEmployee.getName());
            prStmt.setInt(2, newEmployee.getSalary());
            prStmt.setString(3, newEmployee.getDepartmentName());
            prStmt.setString(4, email);
            prStmt.executeUpdate();

            prStmt.close();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an employee.
     * @param email employee's email
     * @return whether the operation was successful
     * @throws DbValueException if such employee doesn't exist
     */
    public boolean removeEmployee(String email) throws DbValueException {

        try {
            Connection connection = DriverManager.getConnection(URL);

            Statement stmt = connection.createStatement();

            String checkEmp = String.format("SELECT emp_name FROM employees WHERE email = '%s'", email);
            ResultSet rs = stmt.executeQuery(checkEmp);
            if (!rs.next()) {
                throw new DbValueException("This employee doesn't exist!");
            }
            stmt.close();

            PreparedStatement prStmt = connection.prepareStatement("DELETE FROM employees WHERE email = ?");
            prStmt.setString(1, email);
            prStmt.executeUpdate();

            prStmt.close();
            connection.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
