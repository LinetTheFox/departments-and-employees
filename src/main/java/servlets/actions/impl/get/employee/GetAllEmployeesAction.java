package servlets.actions.impl.get.employee;

import domain.Department;
import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import service.EmployeeService;
import servlets.actions.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Action class representing action of retrieving all {@link Employee} entries.
 * @author linet
 */
public class GetAllEmployeesAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(GetAllEmployeesAction.class);

    private final DepartmentService departmentService = new DepartmentService();
    private final EmployeeService employeeService = new EmployeeService();

    private Long id;

    public GetAllEmployeesAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Department department;
            List<Employee> employeeList;
            //
            if (this.id == -1L) {
                 employeeList = employeeService.listAllEmployees();
                request.setAttribute("departmentName", "All");
                request.setAttribute("obtainedFromAllEmployees", true);
            } else {
                department = departmentService.getDepartmentById(id);
                employeeList = employeeService.listEmployeesFromDepartment(department);
                request.setAttribute("departmentName", department.getName());
            }
            request.setAttribute("employeesList", employeeList);
            request.setAttribute("departmentId", id);
            request.setAttribute("listAll", true);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/employees.jsp");
            dispatcher.forward(request, response);
        } catch (DataException e) {
            LOG.error("Error 500: " + e.getMessage(), e);
            response.sendError(500);
        } catch (ValidationException e) {
            LOG.error("Error 404: " + e.getMessage(), e);
            response.sendError(404);
        }
    }
}
