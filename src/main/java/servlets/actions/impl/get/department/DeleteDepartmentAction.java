package servlets.actions.impl.get.department;

import domain.Department;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import servlets.actions.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Action class representing action of deleting a {@link Department}.
 * @author linet
 */
public class DeleteDepartmentAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteDepartmentAction.class);

    private final DepartmentService departmentService = new DepartmentService();

    private Long id;

    public DeleteDepartmentAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Department department;
        try {
            department = departmentService.getDepartmentById(id);
            departmentService.removeDepartment(department);
            response.sendRedirect("/dae/departments");
            return;
        } catch (ValidationException | DataException e) {
            LOG.error("Error 500:" + e.getMessage(), e);
            response.sendError(500);
            return;
        }
    }
}