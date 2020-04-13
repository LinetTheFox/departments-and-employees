package servlets.actions.impl.post.employee;

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

import static util.ServletUtil.*;

/**
 *  * Action class representing action of editing a {@link Employee} entry.
 * @author linet
 */
public class UpdateEmployeeAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateEmployeeAction.class);

    private final DepartmentService departmentService = new DepartmentService();
    private final EmployeeService employeeService = new EmployeeService();

    private Long id;

    public UpdateEmployeeAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String params[] = getParams(request);
        // validating
        Employee newEmployee;
        try {
            newEmployee = validateAndReturnEmployee(params);
        } catch (ValidationException e) {
            setAttributes(request, params);
            LOG.info("Received invalid data. Returning to the form.");
            request.setAttribute("message", e.getMessage());
            List<Department> departments;
            try {
                departments = departmentService.listAllDepartments();
            } catch (DataException ex) {
                LOG.error("Error 500: ." + ex.getMessage(), ex);
                response.sendError(500);
                return;
            }
            request.setAttribute("departmentsList", departments);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/employeeEditForm.jsp");
            dispatcher.forward(request, response);
            return;
        }
        // updating domain object and load it into database
        try {
            Employee oldEmployee = employeeService.getEmployeeById(id);
            employeeService.editEmployee(oldEmployee, newEmployee);
        } catch (DataException e) {
            LOG.error("Could not add the new employee to database.", e);
            response.sendError(500);
            return;
        } catch (ValidationException e) {
            LOG.info("Received invalid data. Returning to the form.");
            request.setAttribute("message", e.getMessage());
            setAttributes(request, params);
            List<Department> departments;
            try {
                departments = departmentService.listAllDepartments();
            } catch (DataException ex) {
                LOG.error("Error 500: ." + ex.getMessage(), ex);
                response.sendError(500);
                return;
            }
            request.setAttribute("departmentsList", departments);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentEditForm.jsp");
            dispatcher.forward(request, response);
            return;
        }
        response.sendRedirect("/dae/departments");
    }
}
