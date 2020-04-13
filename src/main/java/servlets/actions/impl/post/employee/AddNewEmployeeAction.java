package servlets.actions.impl.post.employee;

import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;
import servlets.actions.Action;
import util.ServletUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static util.ServletUtil.setAttributes;

/**
 * Action class representing action of adding a new {@link Employee} entry.
 * @author linet
 */
public class AddNewEmployeeAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(AddNewEmployeeAction.class);

    private final EmployeeService employeeService = new EmployeeService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] params = ServletUtil.getParams(request);
        // validating
        Employee employee;
        try {
            employee = ServletUtil.validateAndReturnEmployee(params);
        } catch (ValidationException e) {
            setAttributes(request, params);
            request.setAttribute("message", e.getMessage());
            LOG.info("Received invalid data. Returning to the form.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/employeeAddForm.jsp");
            dispatcher.forward(request, response);
            return;
        }
        // creating domain object and load it into database
        try {
            employeeService.addEmployee(employee);
        } catch (DataException e) {
            LOG.error("Error 500: ." + e.getMessage(), e);
            response.sendError(500);
            return;
        } catch (ValidationException e) {
            LOG.info("Received invalid data. Returning to the form.");
            request.setAttribute("message", e.getMessage());
            setAttributes(request, params);
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/forms/departmentAddForm.jsp");
            dispatcher.forward(request, response);
            return;
        }
        response.sendRedirect("/dae/departments");
    }
}
