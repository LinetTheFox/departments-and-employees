package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets.actions.Action;
import servlets.actions.ActionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linet
 */
public class EmployeeServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServlet.class);

    private Action action;
    private boolean validUrl = false;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        // if path is ./employees/add
        if (pathParts.length == 2 && pathParts[1].equals("add")) {
            action = ActionManager.addNewEmployee();
            validUrl = true;
        }

        // if path is ./employees/<id>/update
        else if (pathParts.length == 3 && pathParts[1].matches("\\d+") && pathParts[2].equals("update")) {
            Long id = Long.parseLong(pathParts[1]);

            action = ActionManager.updateEmployee(id);
            validUrl = true;
        }
        if (validUrl) {
            action.execute(request, response);
        } else {
            response.sendError(404);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // if path is ./employees
        // List all employees there are
        if (pathInfo == null || pathInfo.equals("")) {
            action = ActionManager.getAllEmployees(-1L);
            validUrl = true;
        } else {

            String[] pathParts = pathInfo.split("/");

            // if path is ./employees/<id> or ./employees/<action>
            if (pathParts.length == 2) {

                // if path is ./employees/add
                if (pathParts[1].equals("add")) {
                    action = ActionManager.forwardToEmployeeAddForm();
                    validUrl = true;
                }
                // if mapping isn't 'add' or a number - send error 400
                else if (!pathParts[1].matches("-?\\d+")) {
                    LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                    response.sendError(404);
                    return;
                }
                // if path is ./employees/<id>
                else {
                    Long id = Long.parseLong(pathParts[1]);
                    action = ActionManager.getSingleEmployeeAction(id);
                    validUrl = true;
                }
            }
            // if path is ./employees/<id>/<action>
            else if (pathParts.length == 3) {

                if (!pathParts[1].matches("\\d+")) {
                    LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                    response.sendError(404);
                    return;
                }

                Long id = Long.parseLong(pathParts[1]);
                switch (pathParts[2]) {
                    case "update": {
                        action = ActionManager.forwardToEmployeeEditForm(id);
                        validUrl = true;
                        break;
                    }
                    case "delete": {
                        action = ActionManager.deleteEmployee(id);
                        validUrl = true;
                        break;
                    }
                    default: {
                        LOG.warn("Client followed " + pathInfo + ". Sent error 404.");
                        response.sendError(404);
                        return;
                    }
                }
            }
        }
        if (validUrl) {
            action.execute(request, response);
        } else {
            response.sendError(404);
        }
    }
}