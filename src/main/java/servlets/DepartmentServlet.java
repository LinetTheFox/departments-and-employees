package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlets.actions.Action;
import servlets.actions.ActionManager;

import javax.servlet.RequestDispatcher;
import java.io.IOException;

/**
 * @author linet
 */
public class DepartmentServlet extends javax.servlet.http.HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServlet.class);

    private Action action;
    private boolean validUrl = false;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        // if path is ./departments/add
        if (pathParts.length == 2 && pathParts[1].equals("add")) {
            action = ActionManager.addNewDepartment();
            validUrl = true;
        }
        // if path is ./departments/<id>/update
        else if (pathParts.length == 3 && pathParts[1].matches("\\d+") && pathParts[2].equals("update")) {
            Long id = Long.parseLong(pathParts[1]);
            action = ActionManager.updateDepartment(id);
            validUrl = true;
        }
        if (validUrl) {
            action.execute(request, response);
        } else {
            response.sendError(404);
        }
    }



    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // if path is ./departments
        // Forward to the page that displays a table with all departments
        if (pathInfo == null || pathInfo.equals("")) {
            action = ActionManager.getAllDepartments();
            validUrl = true;
        } else {

            String[] pathParts = pathInfo.split("/");

            // if path is ./departments/<id> or ./departments/<action>
            if (pathParts.length == 2) {

                // if path is ./departments/add
                if (pathParts[1].equals("add")) {
                    action = ActionManager.forwardToDepartmentAddForm();
                    validUrl = true;
                }

                // if mapping isn't 'add' or a number - send error 400
                else if (!pathParts[1].matches("\\d+")) {
                    LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                    response.sendError(404);
                    return;
                }

                // if path is ./departments/<id>
                else {
                    Long id = Long.parseLong(pathParts[1]);
                    action = ActionManager.getAllEmployees(id);
                    validUrl = true;
                }
            }

            // if path is ./departments/<id>/<action>
            else if (pathParts.length == 3) {

                if (!pathParts[1].matches("\\d+")) {
                    LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                    response.sendError(404);
                    return;
                }

                Long id = Long.parseLong(pathParts[1]);

                switch (pathParts[2]) {
                    case "update": {
                        action = ActionManager.forwardToDepartmentEditForm(id);
                        validUrl = true;
                        break;
                    }
                    case "delete": {
                        action = ActionManager.deleteDepartment(id);
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
