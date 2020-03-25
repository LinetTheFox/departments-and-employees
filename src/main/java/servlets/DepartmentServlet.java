package servlets;

import dao.DepartmentDao;
import domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author linet
 */
public class DepartmentServlet extends javax.servlet.http.HttpServlet {

    private static Logger LOG = LoggerFactory.getLogger(DepartmentServlet.class);

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        DepartmentDao dao = new DepartmentDao();

        List<Department> departments;
        departments = dao.getAll();

        request.setAttribute("departmentsList", departments);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/departments.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
