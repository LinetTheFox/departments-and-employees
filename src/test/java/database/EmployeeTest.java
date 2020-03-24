package database;

import domain.Employee;
import exception.DatabaseException;
import exception.DbValueException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author linet
 */
public class EmployeeTest {

    static DbManager dbManager;

    @BeforeAll
    static void setup() {
        try {
            dbManager = DbManager.getInstance();
            dbManager.addDepartment("DepartA");
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    @AfterAll
    static void finish() {
        try {
            dbManager.removeDepartment("DepartA");
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void createAndRemoveEmployee() {
        try {
            dbManager.addEmployee(new Employee("abc", new Date(), "def", 500, "DepartA"));
            dbManager.removeEmployee("def");
        } catch (DbValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cantCreateAnEmployeeWithSameEmail() {
        try {
            dbManager.addEmployee(new Employee("abc", new Date(), "def", 500, "DepartA"));
            dbManager.addEmployee(new Employee("efg", new Date(), "def", 1000, "DepartA"));
            fail("Didn't throw exception.");
        } catch (DbValueException e) {
            try {
                dbManager.removeEmployee("def");
            } catch (DbValueException ex) {
                System.err.println("Fatal test failure.");
                ex.printStackTrace();
                fail(ex.getMessage());
            }
        }
    }

    @Test
    public void cantCreateAnEmployeeOfInvalidDepartment() {
        try {
            dbManager.addEmployee(new Employee("abc", new Date(), "def", 500, "DepartB"));
            fail("Didn't throw exception");
        } catch (DbValueException e) {
            // do nothing
        }
    }

    @Test
    public void editEmployee() {
        try {
            dbManager.addEmployee(new Employee("abc", new Date(), "def", 500, "DepartA"));
            dbManager.editEmployee(new Employee("xyz", new Date(), "def", 1000, "DepartA"), "def");
            dbManager.removeEmployee("def");
        } catch (DbValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cantEditEmployeeToGiveInvalidDepartment() {
        try {
            dbManager.addEmployee(new Employee("abc", new Date(), "def", 500, "DepartA"));
            dbManager.editEmployee(new Employee("xyz", new Date(), "def", 1000, "DepartB"), "def");
            fail("Didn't throw exception.");
        } catch (DbValueException e) {
            try {
                dbManager.removeEmployee("def");
            } catch (DbValueException ex) {
                System.err.println("Fatal test error.");
                ex.printStackTrace();
                fail(ex.getMessage());
            }
        }
    }

    @Test
    public void cantEditNotExistingEmployee() {
        try {
            dbManager.editEmployee(new Employee("abc", new Date(), "def", 500, "DepartA"), "doesntExist");
            fail("Didn't throw exception.");
        } catch (DbValueException e) {
            // do nothing
        }
    }

    @Test
    public void cantRemoveNotExistingEmployee() {
        try {
            dbManager.removeEmployee("doesntExist");
            fail("Didn't throw exception.");
        } catch (DbValueException e) {
            // do nothing
        }
    }

    @Test
    public void correctlyListAllEmployees() {
        try {
            Employee a = new Employee("balik", new Date(), "def", 500, "DepartA");
            Employee b = new Employee("linet", new Date(), "lol", 200, "DepartA");
            Employee c = new Employee("sanya", new Date(), "areYouOK", 10000, "DepartA");
            dbManager.addEmployee(a);
            dbManager.addEmployee(b);
            dbManager.addEmployee(c);
            List<Employee> list = dbManager.listEmployees("DepartA");
            dbManager.removeEmployee("def");
            dbManager.removeEmployee("lol");
            dbManager.removeEmployee("areYouOK");

            // Employee overrides equals() and hashCode() so we can compare them by values.
            // The way how this loop works:
            // If we have all the same employees we put in database, each will get into condition which
            // 'continues' the loop and never will get down to fail() method.
            for (Employee e : list) {
                if (a.equals(e)) {
                    continue;
                }
                if (b.equals(e)) {
                    continue;
                }
                if (c.equals(e)) {
                    continue;
                }
                fail("Database returned wrong data.");
            }
        } catch (DbValueException e) {
            fail(e.getMessage());
        }
    }


}
