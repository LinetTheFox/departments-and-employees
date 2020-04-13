package servlets.actions.impl.get.employee;

import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;
import servlets.actions.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Action class representing action of deleting an {@link Employee}.
 * @author linet
 */
public class DeleteEmployeeAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteEmployeeAction.class);

    private final EmployeeService employeeService = new EmployeeService();

    private Long id;

    public DeleteEmployeeAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee employee;
        try {
            employee = employeeService.getEmployeeById(id);
            employeeService.removeEmployee(employee);
            response.sendRedirect("/dae/departments/");
        } catch (ValidationException | DataException e) {
            LOG.error("Error 500:" + e.getMessage(), e);
            response.sendError(500);
        }
    }
}
