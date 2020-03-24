package servlets;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author linet
 */
public class DepartmentServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
//        request.getRequestDispatcher("/index.jsp").forward(request, response);
        PrintWriter pw = response.getWriter();
        pw.println("<html>");
        pw.println("<h1>Hewwo :3</h1>");
        pw.println("</html>");
    }
}
