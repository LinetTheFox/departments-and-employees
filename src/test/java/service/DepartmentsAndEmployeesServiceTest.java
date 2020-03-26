package service;

import domain.Department;
import domain.Employee;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author linet
 */
public class DepartmentsAndEmployeesServiceTest {

    static DepartmentService departmentService = new DepartmentService();
    static EmployeeService employeeService = new EmployeeService();

    @BeforeAll
    static void setup() {
        try {
            Department department = new Department("test");
            departmentService.addDepartment(department);
        } catch(Exception e) {
            e.printStackTrace();
            fail("Could not set up tests.", e);
        }
    }

    @AfterAll
    static void shutdown() {
        try {
            Department department = new Department("test");
            departmentService.removeDepartment(department);
        } catch(Exception e) {
            e.printStackTrace();
            fail("Could not shut down tests.", e);
        }
    }

    @Test
    public void createAndRemoveDepartment() {
        Department department = new Department("test_a");

        try {
            departmentService.addDepartment(department);
            departmentService.removeDepartment(department);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cannotCreateSameDepartment() {
        Department department = new Department("test_a");

        try {
            departmentService.addDepartment(department);
            departmentService.addDepartment(department);
            fail("Exception not thrown.");
        } catch (Exception e) {
            try {
                departmentService.removeDepartment(department);
            } catch (Exception ex) {
                fail("Could not finish the test.");
            }
        }
    }

    @Test
    public void editDepartment() {
        Department departmentA = new Department("test_a");
        Department departmentB = new Department("test_b");

        try {
            departmentService.addDepartment(departmentA);
            departmentService.editDepartment(departmentA, departmentB);
            departmentService.removeDepartment(departmentB);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cannotEditNotExistingDepartment() {
        Department departmentA = new Department("test_a");
        Department departmentB = new Department("test_b");

        try {
            departmentService.editDepartment(departmentA, departmentB);
            fail("Exception not thrown.");
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void cannotRemoveNotExistingDepartment() {
        Department department = new Department("test_a");

        try {
            departmentService.removeDepartment(department);
            fail("Exception not thrown.");
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void createAndRemoveEmployee() {
        Employee employee = new Employee("test_a", new Date(), "test_a", 69, "test");

        try {
            employeeService.addEmployee(employee);
            employeeService.removeEmployee(employee);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cannotCreateEmployeeWithSameEmail() {
        Employee employee = new Employee("test_a", new Date(), "test_a", 69, "test");

        try {
            employeeService.addEmployee(employee);
            employeeService.addEmployee(employee);
            fail("Exception not thrown.");
        } catch (Exception e) {
            try {
                employeeService.removeEmployee(employee);
            } catch (Exception ex) {
                fail("Could not finish the test.");
            }
        }
    }

    @Test
    public void cannotCreateEmployeeOfNotExistingDepartment() {
        Employee employee = new Employee("test_a", new Date(), "test_a", 69, "nope");

        try {
            employeeService.addEmployee(employee);
            fail("Exception not thrown");
        } catch(Exception e) {
            //do nothing
        }
    }

    @Test
    public void editEmployee() {
        Employee employeeA = new Employee("test_a", new Date(), "test_a", 69, "test");
        Employee employeeB = new Employee("test_b", new Date(), "test_b", 69, "test");

        try {
            employeeService.addEmployee(employeeA);
            employeeService.editEmployee(employeeA, employeeB);
            employeeService.removeEmployee(employeeB);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void cannotEditToAssignToNotExistingDepartment() {
        Employee employeeA = new Employee("test_a", new Date(), "test_a", 69, "test");
        Employee employeeB = new Employee("test_b", new Date(), "test_b", 69, "nope");

        try {
            employeeService.addEmployee(employeeA);
            employeeService.editEmployee(employeeA, employeeB);
            fail("Exception not thrown.");
        } catch (Exception e) {
            try {
                employeeService.removeEmployee(employeeA);
            } catch (Exception ex) {
                fail("Could not finish the test.");
            }
        }
    }

    @Test
    public void cannotEditNotExistingEmployee() {
        Employee employeeA = new Employee("test_a", new Date(), "test_a", 69, "test");
        Employee employeeB = new Employee("test_b", new Date(), "test_b", 69, "nope");

        try {
            employeeService.editEmployee(employeeA, employeeB);
            fail("Exception not thrown.");
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void cannotRemoveNotExistingEmployee() {
        Employee employee = new Employee("test_a", new Date(), "test_a", 69, "test");

        try {
            employeeService.removeEmployee(employee);
            fail("Exception not thrown.");
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void editingDepartmentEditsItsEmployees() {
        Department departmentA = new Department("test_a");
        Department departmentB = new Department("test_b");
        Employee employee = new Employee("test_a", new Date(), "test_a", 69, "test_a");

        try {
            departmentService.addDepartment(departmentA);
            employeeService.addEmployee(employee);
            departmentService.editDepartment(departmentA, departmentB);
            departmentService.removeDepartment(departmentB);
        } catch (Exception e) {
            fail(e.getMessage());
        }

        try {
            employeeService.removeEmployee(employee);
            fail("Exception not thrown.");
        } catch (Exception e) {
            // do nothing
        }
    }

    @Test
    public void removingDepartmentRemovesItsEmployees() {
        Department department = new Department("test_a");
        Employee employee = new Employee("test_a", new Date(), "test_a", 69, "test_a");

        try {
            departmentService.addDepartment(department);
            employeeService.addEmployee(employee);
            departmentService.removeDepartment(department);
        } catch(Exception e) {
            fail(e.getMessage());
        }

        try {
            employeeService.removeEmployee(employee);
            fail("Exception not thrown");
        } catch(Exception e) {
            // do nothing
        }
    }
}
