package servlets;

import dao.DepartmentDao;
import domain.Department;
import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import service.EmployeeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author linet
 */
public class DepartmentServlet extends javax.servlet.http.HttpServlet {

    DepartmentService departmentService = new DepartmentService();
    EmployeeService employeeService = new EmployeeService();

    private static Logger LOG = LoggerFactory.getLogger(DepartmentServlet.class);

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        // if path is ./departments/add
        // Add the received department to database and forward back to the list of departments
        // If value already exists - return to the form
        if (pathParts.length == 2 && pathParts[1].equals("add")) {
            String name = request.getParameter("name");
            Department department = new Department(name);
            try {
                departmentService.addDepartment(department);
            } catch (DataException e) {
                LOG.error("Could not add the new department to database.", e);
                response.sendError(500);
            } catch (ValidationException e) {
                LOG.info("Received invalid data. Returning to the form.");
                request.setAttribute("message", e.getMessage());
                request.setAttribute("department", department);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentAddForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            response.sendRedirect("/dae/departments");
            return;
        }

        // if path is ./departments/<id>/update
        // Update the department info and forward back to the list of departments
        // If value already exists - return to the form
        if (pathParts.length == 3 && pathParts[1].matches("\\d+") && pathParts[2].equals("update")) {
            String name = request.getParameter("name");
            Long id = Long.parseLong(pathParts[1]);
            try {
                Department oldDepartment = departmentService.getDepartmentById(id);
                Department newDepartment = new Department(name);
                departmentService.editDepartment(oldDepartment, newDepartment);
            } catch (DataException e) {
                LOG.error("Could not add the new department to database.", e);
                response.sendError(500);
            } catch (ValidationException e) {
                LOG.info("Received invalid data. Returning to the form.");
                request.setAttribute("message", e.getMessage());
                request.setAttribute("department", new Department(name));
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentEditForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            response.sendRedirect("/dae/departments");
            return;
        }

        response.sendError(500);
    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // if path is ./departments
        // Forward to the page that displays a table with all departments
        if (pathInfo == null || pathInfo.equals("")) {
            List<Department> departments;
            try {
                departments = departmentService.listAllDepartments();
                request.setAttribute("departmentsList", departments);
            } catch (DataException e) {
                LOG.error("Error 500: " + e.getMessage() , e);
                response.sendError(500, e.getMessage());
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/departments.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");

        // if path is ./departments/<id> or ./departments/<action>
        // Depending on which one
        if (pathParts.length == 2) {

            // if path is ./departments/add
            // Forward to the form for adding a department
            if (pathParts[1].equals("add")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentAddForm.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // if mapping isn't 'add' or a number - send error 400
            if (!pathParts[1].matches("\\d+")) {
                LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                response.sendError(404);
            }

            // if path is ./departments/<id>
            // Forward to the page that displays a table with all employees of given department.
            Long id = Long.parseLong(pathParts[1]);
            List<Employee> employees;
            String departmentName = "";
            try {
                departmentName = departmentService.getDepartmentById(id).getName();
            } catch(DataException e) {
                LOG.error("Error 404: " + e.getMessage(), e);
                response.sendError(404, e.getMessage());
            }
            try {
                employees = employeeService.listEmployeesFromDepartment(new Department(departmentName));

                request.setAttribute("employeesList", employees);
                request.setAttribute("departmentName", departmentName);
            } catch (DataException e) {
                LOG.error("Error 500: " + e.getMessage(), e);
                response.sendError(500, e.getMessage());
            } catch (ValidationException e) {
                LOG.error("Error 500:" + e.getMessage(), e);
                response.sendError(500, e.getMessage());
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/employees.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // if path is /departments/<id>/<action>
        if (pathParts.length == 3) {

            if (!pathParts[1].matches("\\d+")) {
                LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                response.sendError(404);
            }

            Long id = Long.parseLong(pathParts[1]);

            switch (pathParts[2]) {

                case "update": {
                    Department department;
                    try {
                        department = departmentService.getDepartmentById(id);
                        request.setAttribute("department", department);
                    } catch (DataException e) {
                        response.sendError(500);
                    }

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentEditForm.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
                case "delete": {
                    Department department;
                    try {
                        department = departmentService.getDepartmentById(id);
                        departmentService.removeDepartment(department);
                    } catch (ValidationException | DataException e) {
                        LOG.error("Error 500:" + e.getMessage(), e);
                        response.sendError(500, e.getMessage());
                    }

                    response.sendRedirect("/dae/departments");
                    return;
                }
                default: {
                    response.sendError(404);
                }
            }
        }

        response.sendError(404);
    }
}
