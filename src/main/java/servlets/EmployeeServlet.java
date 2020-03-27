package servlets;

import domain.Department;
import domain.Employee;
import exception.DataException;
import exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DepartmentService;
import service.EmployeeService;
import util.DateUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author linet
 */
public class EmployeeServlet extends HttpServlet {

    DepartmentService departmentService = new DepartmentService();
    EmployeeService employeeService = new EmployeeService();

    private static Logger LOG = LoggerFactory.getLogger(EmployeeServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        // if path is ./employees/add
        // Add the received department to database and forward back to the list of departments
        // If value already exists or invalid data was sent - return to the form
        if (pathParts.length == 2 && pathParts[1].equals("add")) {
            String[] params = getParams(request);
            // validating
            Employee employee;
            try {
                employee = validateAndReturnEmployee(params);
            } catch (ValidationException e) {
                setAttributes(request, params);
                request.setAttribute("message", e.getMessage());
                LOG.info("Received invalid data. Returning to the form.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/employeeAddForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            // creating domain object and load it into database
            try {
                employeeService.addEmployee(employee);
            } catch (DataException e) {
                LOG.error("Error 500: ." + e.getMessage(), e);
                response.sendError(500);
                return;
            } catch (ValidationException e) {
                LOG.info("Received invalid data. Returning to the form.");
                request.setAttribute("message", e.getMessage());
                setAttributes(request, params);
                RequestDispatcher dispatcher =
                        request.getRequestDispatcher("/forms/departmentAddForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            response.sendRedirect("/dae/departments");
            return;
        }

        // if path is ./employees/<id>/update
        // Update the department info and forward back to the list of departments
        // If value already exists - return to the form
        if (pathParts.length == 3 && pathParts[1].matches("\\d+") && pathParts[2].equals("update")) {
            String name = request.getParameter("name");
            Long id = Long.parseLong(pathParts[1]);

            String params[] = getParams(request);
            // validating
            Employee newEmployee;
            try {
                newEmployee = validateAndReturnEmployee(params);
            } catch (ValidationException e) {
                setAttributes(request, params);
                LOG.info("Received invalid data. Returning to the form.");
                request.setAttribute("message", e.getMessage());
                List<Department> departments;
                try {
                    departments = departmentService.listAllDepartments();
                } catch (DataException ex) {
                    LOG.error("Error 500: ." + ex.getMessage(), ex);
                    response.sendError(500);
                    return;
                }
                request.setAttribute("departmentsList", departments);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/employeeEditForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            // updating domain object and load it into database
            try {
                Employee oldEmployee = employeeService.getEmployeeById(id);
                employeeService.editEmployee(oldEmployee, newEmployee);
            } catch (DataException e) {
                LOG.error("Could not add the new employee to database.", e);
                response.sendError(500);
                return;
            } catch (ValidationException e) {
                LOG.info("Received invalid data. Returning to the form.");
                request.setAttribute("message", e.getMessage());
                setAttributes(request, params);
                List<Department> departments;
                try {
                    departments = departmentService.listAllDepartments();
                } catch (DataException ex) {
                    LOG.error("Error 500: ." + ex.getMessage(), ex);
                    response.sendError(500);
                    return;
                }
                request.setAttribute("departmentsList", departments);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/departmentEditForm.jsp");
                dispatcher.forward(request, response);
                return;
            }
            response.sendRedirect("/dae/departments");
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        // if path is ./employees
        // List all employees there are
        if (pathInfo == null || pathInfo.equals("")) {
            try {
                List<Employee> employeeList = employeeService.listAllEmployees();
                request.setAttribute("employeesList", employeeList);
                request.setAttribute("departmentName", "All");
                request.setAttribute("listAll", true);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/employees.jsp");
                dispatcher.forward(request, response);
                return;
            } catch (DataException e) {
                LOG.error("Error 500: " + e.getMessage(), e);
                response.sendError(500, e.getMessage());
                return;
            }
        }

        String[] pathParts = pathInfo.split("/");

        // if path is ./employees/<id> or ./employees/<action>
        // Depending on which one
        if (pathParts.length == 2) {

            // if path is ./employees/add
            // Forward to the form for adding a department
            if (pathParts[1].equals("add")) {
                String idString = request.getParameter("departmentId");
                Long id = Long.parseLong(idString);
                try {

                    if (!idString.matches("[0-9]+")) {
                        LOG.error("Error 400: Invalid department id");
                        response.sendError(400);
                        return;
                    }
                    Department department = departmentService.getDepartmentById(id);
                    request.setAttribute("departmentName", department.getName());
                } catch (DataException e) {
                    LOG.error("Error 500: " + e.getMessage(), e);
                    response.sendError(500);
                    return;
                }
                RequestDispatcher dispatcher =
                        request.getRequestDispatcher("/forms/employeeAddForm.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // if mapping isn't 'add' or a number - send error 400
            if (!pathParts[1].matches("\\d+")) {
                LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                response.sendError(404);
                return;
            }

            // if path is ./employees/<id>
            // Display a single employee. Using a list to reuse the employees.jsp
            Long id = Long.parseLong(pathParts[1]);
            Employee employee;
            try {
                employee = employeeService.getEmployeeById(id);
            } catch(DataException e) {
                LOG.error("Error 404: " + e.getMessage(), e);
                response.sendError(404, e.getMessage());
                return;
            }
            List<Employee> pseudoList = new ArrayList<>();
            pseudoList.add(employee);
            request.setAttribute("employeesList", pseudoList);
            request.setAttribute("receivedById", true);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/employees.jsp");
            dispatcher.forward(request, response);
            return;
        }
        // if path is ./employees/<id>/<action>
        if (pathParts.length == 3) {

            if (!pathParts[1].matches("\\d+")) {
                LOG.warn("Client attempted to follow " + pathInfo + ". Sent error 404.");
                response.sendError(404);
                return;
            }

            Long id = Long.parseLong(pathParts[1]);

            switch (pathParts[2]) {

                case "update": {
                    try {
                        Employee employee = employeeService.getEmployeeById(id);
                        List<Department> departments = departmentService.listAllDepartments();
                        setAttributes(request, employeeToStringArraay(employee));
                        request.setAttribute("departmentsList", departments);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/forms/employeeEditForm.jsp");
                        dispatcher.forward(request, response);
                        return;
                    } catch (DataException e) {
                        LOG.error("Error 500: " + e.getMessage(), e);
                        response.sendError(500);
                        return;
                    }
                }
                case "delete": {
                    Employee employee;
                    try {
                        employee = employeeService.getEmployeeById(id);
                        employeeService.removeEmployee(employee);
                        response.sendRedirect("/dae/departments/");
                        return;
                    } catch (ValidationException | DataException e) {
                        LOG.error("Error 500:" + e.getMessage(), e);
                        response.sendError(500);
                        return;
                    }
                }
                default: {
                    LOG.warn("Client followed " + pathInfo + ". Sent error 404.");
                    response.sendError(404);
                    return;
                }
            }
        }
        response.sendError(404);
    }

    // util methods just for this servlet to handle 5 strings at time to make
    // doPost() smaller.
    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    private Employee validateAndReturnEmployee(String params[]) throws ValidationException {

        // validating all of them to be not null and not empty
        for (String p : params) {
            if (p == null || p.equals("")) {
                throw new ValidationException("All the fields must be filled");
            }
        }

        boolean valid = true;
        StringBuilder message = new StringBuilder("Invalid fields: ");

        // validating date (getDate() throws ValidationException too)
        Date dateObj = new Date(); // constructor to bypass compile error.

        try {
            dateObj = DateUtil.getDate(params[2]);
        } catch (ParseException e) {
            valid = false;
            message.append("date, ");
        }

        // validating email
        if (!params[1].matches("^[\\.A-Za-z0-9_]+@[a-z]+.[a-z]+$")) {
            valid = false;
            message.append("email, ");
        }

        // validating salary
        if (!params[3].matches("\\d+")) {
            valid = false;
            message.append("salary, ");
        }
        // Name doesn't need validation here and departmentName is received from the list
        if (!valid) {
            message.replace(message.length()-2, message.length(), ".");
            throw new ValidationException(message.toString());
        }
        int salaryInt = Integer.parseInt(params[3]);
        return new Employee(params[0], dateObj, params[1], salaryInt, params[4]);
    }

    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    private String[] employeeToStringArraay(Employee employee) {
        String[] params = new String[5];
        params[0] = employee.getName();
        params[1] = employee.getEmail();
        params[2] = DateUtil.dateToString(employee.getDate());
        params[3] = employee.getSalary().toString();
        params[4] = employee.getDepartmentName();
        return params;
    }

    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    private String[] getParams(HttpServletRequest request) {
        String[] params = new String[5];
        params[0] = request.getParameter("name");
        params[1] = request.getParameter("email");
        params[2] = request.getParameter("date");
        params[3] = request.getParameter("salary");
        params[4] = request.getParameter("departmentName");
        return params;
    }

    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    private void setAttributes(HttpServletRequest request, String[] attributes) {
        request.setAttribute("name", attributes[0]);
        request.setAttribute("email", attributes[1]);
        request.setAttribute("date", attributes[2]);
        request.setAttribute("salary", attributes[3]);
        request.setAttribute("departmentName", attributes[4]);
    }
}
