package servlets.actions.impl.get.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets.actions.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Action class representing action of forwarding the user to
 * {@link domain.Department} adding form
 * @author linet
 */
public class ForwardToDepartmentAddFormAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ForwardToDepartmentAddFormAction.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentAddForm.jsp");
        dispatcher.forward(request, response);
    }
}
