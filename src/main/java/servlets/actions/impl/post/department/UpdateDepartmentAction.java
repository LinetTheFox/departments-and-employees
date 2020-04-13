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
 * Action class representing action of editing a {@link Department} entry.
 * @author linet
 */
public class UpdateDepartmentAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateDepartmentAction.class);

    private final DepartmentService departmentService = new DepartmentService();

    private Long id;

    public UpdateDepartmentAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        try {
            Department oldDepartment = departmentService.getDepartmentById(this.id);
            Department newDepartment = new Department(name);
            departmentService.editDepartment(oldDepartment, newDepartment);
            response.sendRedirect("/dae/departments");
        } catch (DataException e) {
            LOG.error("Could not add the new department to database.", e);
            response.sendError(500);
        } catch (ValidationException e) {
            LOG.info("Received invalid data. Returning to the form.");
            request.setAttribute("message", e.getMessage());
            request.setAttribute("department", new Department(name));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentEditForm.jsp");
            dispatcher.forward(request, response);
        }
    }
}
