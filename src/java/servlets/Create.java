package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Clients;
import repositories.ClientService;
import utils.UsernameValidator;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@WebServlet(name = "Create", urlPatterns = {"/create"})
public class Create extends HttpServlet {

    @EJB
    UsernameValidator validator;
    @EJB
    ClientService clientService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");    
            out.println("<title>Create</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Create new client</h3>");
            out.println("<div>");
            out.println("<form action=\"\" method=\"POST\">");
            out.println("<label for=\"clientname\">Name:</label>");
            out.println("<input type=\"text\" id=\"clientname\" name=\"clientname\" /><br>Разрешены символы: а-я, А-Я,-,.<br>");
            out.println("<h5>Физлицо или юрлицо?</h5>");
            out.println("<p>");
            out.println("<input type=\"radio\" value=\"физлицо\" checked name=\"clienttype\"/>физлицо");
            out.println("</p>");
            out.println("<p>");
            out.println("<input type=\"radio\" value=\"юрлицо\" name=\"clienttype\"/>юрлицо");
            out.println("</p>");
            out.println("<p>");
            out.println("</div>");
            out.println("<p>");
            out.println("</p>");
            out.println("<input type=\"submit\" value=\"create\" formaction=\"create\" formmethod=\"POST\" />");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        int clientid = clientService.getMaxId()+1;
        System.out.println("new client id = " + clientid);
        String clientname = request.getParameter("clientname");
        String clienttype = request.getParameter("clienttype");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        Date regdate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        System.out.println(regdate.toString());
        System.out.println("clientname = " + clientname
                + ", clienttype = " + clienttype
                + ", regdate = " + regdate);
        if(clientname.isEmpty()||clientname.length()==0||clientname.length()>100||!validator.isValid(clientname)){
            String message = "Вы неправильно ввели данные:\nИмя клиента может состоять из кириллических символов,  точки, запятой, тире и пробела";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/error").forward(request, response);
        } else {
            Clients client = new Clients(clientid, clientname, clienttype, regdate);
            clientService.saveClient(client);
            response.sendRedirect("http://localhost:8080/J200_Lab1/viewlist");
        }
    }
}