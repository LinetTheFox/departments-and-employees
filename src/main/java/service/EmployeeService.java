package service;

import dao.DepartmentDao;
import dao.EmployeeDao;
import domain.Department;
import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static util.ServiceUtil.*;

/**
 * @author linet
 */
public class EmployeeService {

    private DepartmentDao departmentDao = new DepartmentDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    private static Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    public List<Employee> listAllEmployees() throws DataException {

        List<Employee> employees = employeeDao.getAll();
        if (employees == null) {
            LOG.error("Could not retrieve employees list.");
            throw new DataException("Could not retrieve data");
        }
        return employees;
    }

    public List<Employee> listEmployeesFromDepartment(Department department) throws ValidationException, DataException {

        List<Department> departments = departmentDao.getAll();

        if (departments == null) {
            LOG.error("Could not retrieve departments list.");
            throw new DataException("Could not retrieve data");
        }

        if (!departmentExists(department, departments)) {
            LOG.error("Validation: department doesn't exist.");
            throw new ValidationException("This department doesn't exist!");
        }

        List<Employee> employees = employeeDao.getAll();

        return employees.stream()
                .filter(e -> e.getDepartmentName().equals(department.getName()))
                .collect(Collectors.toList());
    }

    public void addEmployee(Employee employee) throws ValidationException, DataException {

        List<Employee> employees = this.listAllEmployees();
        if (employeeEmailExists(employee, employees)) {
            LOG.error("Validation: employee email already exists!");
            throw new ValidationException("Employee email already exists!");
        }

        List<Department> departments = departmentDao.getAll();
        if (departments == null) {
            LOG.error("Could not retrieve all departments");
            throw new DataException("Could not retrieve data");
        }
        Department newEmployeeDepartment = new Department(employee.getDepartmentName());
        if (!departmentExists(newEmployeeDepartment, departments)) {
            LOG.error("Validation: such department doesn't exist");
            throw new ValidationException("Such department doesn't exist!");
        }

        employeeDao.save(employee);
    }

    public void editEmployee(Employee oldEmp, Employee newEmp) throws ValidationException, DataException {

        List<Employee> employees = this.listAllEmployees();
        if (!employeeEmailExists(oldEmp, employees)) {
            LOG.error("Validation: such employee doesn't exist.");
            throw new ValidationException("Employee doesn't exist!");
        }
        if (employeeEmailExists(newEmp, employees)) {
            LOG.error("Validation: employee email already exists.");
            throw new ValidationException("Employee email already exists!");
        }

        List<Department> departments = departmentDao.getAll();
        if (departments == null) {
            LOG.error("Could not retrieve all departments.");
            throw new DataException("Could not retrieve data");
        }
        Department newEmployeeDepartment = new Department(newEmp.getDepartmentName());
        if (!departmentExists(newEmployeeDepartment, departments)) {
            LOG.error("Validation: such department doesn't exist");
            throw new ValidationException("Department doesn't exist");
        }

        Long id = findIdOfEmployee(oldEmp, employees);
        employeeDao.update(id, newEmp);
    }

    public void removeEmployee(Employee employee) throws DataException, ValidationException {

        List<Employee> employees = this.listAllEmployees();
        if (!employeeEmailExists(employee, employees)) {
            LOG.error("Validation: such employee doesn't exist.");
            throw new ValidationException("Employee doesn't exist!");
        }

        Long id = findIdOfEmployee(employee, employees);
        employeeDao.delete(id);
    }
}
