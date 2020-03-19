package database;

import exception.DatabaseException;
import exception.DbValueException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author linet
 */
public class DepartmentTest {

    static DbManager dbManager;

    @BeforeAll
    static void setup() {
        try {
            dbManager = new DbManager();
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void createAndRemoveDepartment() {
        try {
            dbManager.addDepartment("DepartA");
            dbManager.removeDepartment("DepartA");

        } catch (DbValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cantCreateSameDepartment() {
        try {
            dbManager.addDepartment("DepartA");
            dbManager.addDepartment("DepartA");
            fail("Exception not thrown");
        } catch (DbValueException e) {
            try {
                dbManager.removeDepartment("DepartA");
            } catch (DbValueException ex) {
                System.err.println("Fatal test failure");
                ex.printStackTrace();
                fail(ex.getMessage());
            }
        }
    }

    @Test
    public void editADepartment() {
        try {
            dbManager.addDepartment("DepartA");
            dbManager.editDepartment("DepartA", "DepartB");
            dbManager.removeDepartment("DepartB");
        } catch (DbValueException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cannotEditNotExistingDepartment() {
        try {
            dbManager.addDepartment("DepartB");
            dbManager.editDepartment("DepartA", "DepartB");
            fail("Exception not thrown");
        } catch (DbValueException e) {
            try {
                dbManager.removeDepartment("DepartB");
            } catch (DbValueException ex) {
                System.err.println("Fatal test failure.");
                ex.printStackTrace();
                fail(ex.getMessage());
            }
        }
    }

    @Test
    public void cantRemoveNotExistingDepartment() {
        try {
            dbManager.removeDepartment("DepartC");
            fail("Exception not thrown");
        } catch (DbValueException e) {
            // do nothing
        }
    }
}
