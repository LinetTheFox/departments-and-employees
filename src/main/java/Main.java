import dao.DepartmentDao;
import dao.EmployeeDao;
import database.ConnectionFactory;
import domain.Department;
import domain.Employee;
import util.DateUtil;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

/**
 * Main class used to initialize test data in database
 * @author linet
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Connection connection = ConnectionFactory.getConnection();

        String query1 = "create table departments(" +
                        "id long auto_increment primary key not null," +
                        "name varchar(200) unique not null" +
                        ");";
        String query2 = "create table employees(\n" +
                        "id long auto_increment primary key not null,\n" +
                        "name varchar(200) not null,\n" +
                        "hire_date date not null,\n" +
                        "email varchar(50) unique not null,\n" +
                        "salary int not null,\n" +
                        "dep_name varchar(200) not null\n" +
                        ");";

        Statement stmt = connection.createStatement();
        stmt.execute(query1);
        stmt = connection.createStatement();
        stmt.execute(query2);
        connection.close();


        DepartmentDao depDao = new DepartmentDao();
        EmployeeDao empDao = new EmployeeDao();

        depDao.save(new Department("first"));
        depDao.save(new Department("second"));
        depDao.save(new Department("third"));

        empDao.save(new Employee("Vasya", new Date(), "vasya@gmail.com", 20000, "first"));
        empDao.save(new Employee("Petya", new Date(), "petya@gmail.com", 15000, "second"));
        empDao.save(new Employee("Sasha", new Date(), "sanya@gmail.com", 10000, "first"));
        empDao.save(new Employee("Masha", new Date(), "masha@gmail.com", 20000, "third"));
        empDao.save(new Employee("Liza", new Date(), "lizz@gmail.com", 25000, "second"));
        empDao.save(new Employee("Pasha", new Date(), "pavel@gmail.com", 10000, "first"));
        empDao.save(new Employee("Valera", new Date(), "valerich@gmail.com", 30000, "third"));
    }
}
