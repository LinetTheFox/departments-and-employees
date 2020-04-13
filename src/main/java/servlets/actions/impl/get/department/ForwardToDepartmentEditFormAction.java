package servlets.actions.impl.get.department;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import domain.Department;
import exception.DataException;
import service.DepartmentService;
import servlets.actions.Action;

/**
 * Action class representing action of forwarding the user to
 * {@link Department} editing form.
 * @author linet
 */
public class ForwardToDepartmentEditFormAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardToDepartmentEditFormAction.class);

    private final DepartmentService departmentService = new DepartmentService();

    private Long id;

    public ForwardToDepartmentEditFormAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Department department;
        try {
            department = departmentService.getDepartmentById(id);
            request.setAttribute("department", department);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentEditForm.jsp");
            dispatcher.forward(request, response);
        } catch (DataException e) {
            LOG.error("Error 500: " + e.getMessage(), e);
            response.sendError(500);
        }
    }
}