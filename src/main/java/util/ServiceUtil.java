package util;

import domain.Department;
import domain.Employee;

import java.util.List;

/**
 * @author linet
 */
public class ServiceUtil {
    public static boolean departmentExists(Department department, List<Department> departments) {
        for (Department dep : departments) {
            if (dep.getName().equals(department.getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean employeeEmailExists(Employee employee, List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getEmail().equals(employee.getEmail())) {
                return true;
            }
        }
        return false;
    }

    // These are needed because in domain classes' constructors we don't specify id - we only get it from database
    public static Long findIdOfDepartment(Department department, List<Department> departments) {
        for (Department dep : departments) {
            if (dep.getName().equals(department.getName())) {
                return dep.getId();
            }
        }
        return -1L;
    }

    public static Long findIdOfEmployee(Employee employee, List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getName().equals(employee.getName())) {
                return emp.getId();
            }
        }
        return -1L;
    }
}
