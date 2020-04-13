package servlets.actions.impl.post.department;

import domain.Department;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import servlets.actions.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Action class representing action of adding a new {@link Department} entry.
 * @author linet
 */
public class AddNewDepartmentAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(AddNewDepartmentAction.class);

    private final DepartmentService departmentService = new DepartmentService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Department department = new Department(name);
        try {
            departmentService.addDepartment(department);
            response.sendRedirect("/dae/departments");
        } catch (DataException e) {
            LOG.error("Error 500: ." + e.getMessage(), e);
            response.sendError(500);
        } catch (ValidationException e) {
            LOG.info("Received invalid data. Returning to the form.");
            request.setAttribute("message", e.getMessage());
            request.setAttribute("department", department);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentAddForm.jsp");
            dispatcher.forward(request, response);
        }
    }
}
