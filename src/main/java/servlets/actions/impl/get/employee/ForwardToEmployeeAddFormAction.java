package servlets.actions.impl.get.employee;

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
 * Action class representing action of forwarding the user to
 * {@link domain.Employee} adding form.
 * @author linet
 */
public class ForwardToEmployeeAddFormAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardToEmployeeAddFormAction.class);

    private final DepartmentService departmentService = new DepartmentService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("departmentId");
        long id = Long.parseLong(idString);
        try {
            if (!idString.matches("-?[0-9]+")) {
                LOG.error("Error 400: Invalid department id");
                response.sendError(400);
                return;
            }
            if (id > 0) {
                Department department = departmentService.getDepartmentById(id);
                request.setAttribute("departmentName", department.getName());
            } else {
                List<Department> departmentList = departmentService.listAllDepartments();
                request.setAttribute("departmentList", departmentList);
            }
        } catch (DataException e) {
            LOG.error("Error 500: " + e.getMessage(), e);
            response.sendError(500);
            return;
        }
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/forms/employeeAddForm.jsp");
        dispatcher.forward(request, response);
    }
}
