package servlets.actions.impl.get.employee;

import domain.Department;
import domain.Employee;
import exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import service.EmployeeService;
import servlets.actions.Action;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Action class representing action of forwarding the user to
 * {@link domain.Employee} editing form.
 * @author linet
 */
public class ForwardToEmployeeEditFormAction  implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardToEmployeeAddFormAction.class);

    private final DepartmentService departmentService = new DepartmentService();
    private final EmployeeService employeeService = new EmployeeService();

    private Long id;

    public ForwardToEmployeeEditFormAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            List<Department> departments = departmentService.listAllDepartments();
            ServletUtil.setAttributes(request, ServletUtil.employeeToStringArray(employee));
            request.setAttribute("departmentsList", departments);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/employeeEditForm.jsp");
            dispatcher.forward(request, response);
        } catch (DataException e) {
            LOG.error("Error 500: " + e.getMessage(), e);
            response.sendError(500);
        }
    }
}
