package service;

import dao.DepartmentDao;
import dao.EmployeeDao;
import domain.Department;
import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import util.ServiceUtil;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Service class for working with {@link DepartmentDao}
 * @author linet
 */
public class DepartmentService {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentDao departmentDao = new DepartmentDao();
    private final EmployeeDao employeeDao = new EmployeeDao();


    public List<Department> listAllDepartments() throws DataException {

        List<Department> departments = departmentDao.getAll();
        if (departments == null) {
            LOG.error("Could not retrieve departments list.");
            throw new DataException("Could not retrieve data");
        }
        return departments;
    }

    public Department getDepartmentById(Long id) throws DataException {

        Department department = departmentDao.get(id);
        if (department == null) {
            LOG.error("Could not retrieve the department.");
            throw new DataException("Could not retrieve data.");
        }
        return department;
    }

    public void addDepartment(Department department) throws ValidationException, DataException {

        List<Department> departments = this.listAllDepartments();
        if (ServiceUtil.departmentExists(department, departments)) {
            LOG.info("Validation: department already exists.");
            throw new ValidationException("Such department already exists!");
        }

        departmentDao.save(department);
    }

    public void editDepartment(Department oldDep, Department newDep) throws ValidationException, DataException {
        List<Department> departments = this.listAllDepartments();
        if (!ServiceUtil.departmentExists(oldDep, departments)) {
            LOG.info("Validation: department doesn't exist.");
            throw new ValidationException("This department doesn't exist!");
        }
        if (ServiceUtil.departmentExists(newDep, departments)) {
            LOG.error("Validation: department already exists.");
            throw new ValidationException("Such department already exists!");
        }

        Long id = ServiceUtil.findIdOfDepartment(oldDep, departments);
        departmentDao.update(id, newDep);

        List<Employee> employees = employeeDao.getAll();
        if (employees == null) {
            LOG.info("Could not retrieve employees list.");
            throw new DataException("Could not retrieve data");
        }
        employees
                .stream()
                .filter(e -> e.getDepartmentName().equals(oldDep.getName()))
                .peek(e -> e.setDepartmentName(newDep.getName()))
                .forEach(e -> employeeDao.update(e.getId(), e));
    }

    public void removeDepartment(Department department) throws ValidationException, DataException {

        List<Department> departments = this.listAllDepartments();
        if (!ServiceUtil.departmentExists(department, departments)) {
            LOG.info("Validation: department doesn't exist.");
            throw new ValidationException("This department doesn't exist!");
        }

        Long departmentId = ServiceUtil.findIdOfDepartment(department, departments);
        departmentDao.delete(departmentId);

        List<Employee> employees = employeeDao.getAll();
        if (employees == null) {
            LOG.error("Could not retrieve employees list.");
            throw new DataException("Could not retrieve data");
        }

        employees
                .stream()
                .filter(e -> department.getName().equals(e.getDepartmentName()))
                .forEach(e -> {
                        Long employeeId = e.getId();
                        employeeDao.delete(employeeId);
                });
    }
}
