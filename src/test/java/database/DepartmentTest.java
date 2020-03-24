package database;

import exception.DatabaseException;
import exception.DbValueException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author linet
 */
public class DepartmentTest {

    static DbManager dbManager;

    @BeforeAll
    static void setup() {
        try {
            dbManager = DbManager.getInstance();
        } catch (DatabaseException e) {
            fail("e.getMessage");
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

    @Test
    public void correctlyListAllDepartments() {
        try {

            String a = "DepartA";
            String b = "DepartB";
            String c = "DepartC";

            dbManager.addDepartment("DepartA");
            dbManager.addDepartment("DepartB");
            dbManager.addDepartment("DepartC");
            List<String> list = dbManager.listDepartments();
            dbManager.removeDepartment("DepartA");
            dbManager.removeDepartment("DepartB");
            dbManager.removeDepartment("DepartC");

            // The way how this loop works:
            // If we have all the same departments we put in database, each will get into condition which
            // 'continues' the loop and never will get down to fail() method.
            for (String e : list) {
                if (e.equals(a)) {
                    continue;
                }
                if (e.equals(b)) {
                    continue;
                }
                if (e.equals(c)) {
                    continue;
                }
                fail("Database returned wrong data");
            }

        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }
}
