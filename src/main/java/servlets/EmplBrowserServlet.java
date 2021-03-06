package servlets;

import utils.ExecutePLSQL;
import utils.ResultSetDisplay;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Shows all employees from DB
 */
public class EmplBrowserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ResultSet resultSet = null;
        ResultSetMetaData rsm = null;
//        String query = "SELECT * FROM EMPL";
        String query = "select * from EMPL " +
                "START WITH empno=" + request.getSession().getAttribute("empno") +
                " CONNECT BY prior EMPNO = MGR " +
                " ORDER BY EMPNO";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("header.jsp");
        try {
            requestDispatcher.include(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<title>Employees Browsing</title>");
        out.println("<h1 align=\"center\">Employees:</h1>");
        resultSet = ExecutePLSQL.executeQuery(query);
        ResultSetDisplay.displayEditDeleteEmpl(resultSet, out);

        out.close();

        requestDispatcher = request.getRequestDispatcher("footer.jsp");
        try {
            requestDispatcher.include(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

}
