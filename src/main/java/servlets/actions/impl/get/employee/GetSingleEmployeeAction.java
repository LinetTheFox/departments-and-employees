package servlets.actions.impl.get.employee;

import domain.Employee;
import exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.EmployeeService;
import servlets.actions.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class representing action of retrieving a single {@link Employee} entry.
 * @author linet
 */
public class GetSingleEmployeeAction implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(GetSingleEmployeeAction.class);

    private EmployeeService employeeService = new EmployeeService();

    private Long id;

    public GetSingleEmployeeAction(Long id) {
        this.id = id;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }
}
