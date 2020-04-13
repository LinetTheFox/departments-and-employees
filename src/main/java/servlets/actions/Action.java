package servlets.actions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface defining a single action that can be performed in the app -
 * manipulating data or forwarding to another page.
 * @author linet
 */
public interface Action {
    void execute(HttpServletRequest request, HttpServletResponse response)
            throws javax.servlet.ServletException, IOException;
}
