package servlets.actions.impl.get.department;

import domain.Department;
import exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import servlets.actions.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Action class representing action of retrieving list of all {@link Department} entries.
 * @author linet
 */
public class GetAllDepartmentsAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(GetAllDepartmentsAction.class);

    private final DepartmentService departmentService = new DepartmentService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments;
        try {
            departments = departmentService.listAllDepartments();
            request.setAttribute("departmentsList", departments);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/departments.jsp");
            dispatcher.forward(request, response);
        } catch (DataException e) {
            LOG.error("Error 500: " + e.getMessage() , e);
            response.sendError(500);
        }
    }
}
