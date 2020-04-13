package util;

import domain.Employee;
import exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

/**
 * Servlet util class with frequently used actions
 * @author linet
 */
public class ServletUtil {
    // util methods just for this servlet to handle 5 strings at time to make
    // doPost() smaller.
    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    public static Employee validateAndReturnEmployee(String params[]) throws ValidationException {

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
    public static String[] employeeToStringArray(Employee employee) {
        String[] params = new String[5];
        params[0] = employee.getName();
        params[1] = employee.getEmail();
        params[2] = DateUtil.dateToString(employee.getDate());
        params[3] = employee.getSalary().toString();
        params[4] = employee.getDepartmentName();
        return params;
    }

    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    public static String[] getParams(HttpServletRequest request) {
        String[] params = new String[5];
        params[0] = request.getParameter("name");
        params[1] = request.getParameter("email");
        params[2] = request.getParameter("date");
        params[3] = request.getParameter("salary");
        params[4] = request.getParameter("departmentName");
        return params;
    }

    // 0 - name, 1 - email, 2 - date, 3 - salary, 4 - department name
    public static void setAttributes(HttpServletRequest request, String[] attributes) {
        request.setAttribute("name", attributes[0]);
        request.setAttribute("email", attributes[1]);
        request.setAttribute("date", attributes[2]);
        request.setAttribute("salary", attributes[3]);
        request.setAttribute("departmentName", attributes[4]);
    }
}
