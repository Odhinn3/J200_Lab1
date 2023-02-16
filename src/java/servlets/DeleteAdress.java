
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Adresses;
import models.Clients;
import repositories.AdressService;
import repositories.ClientService;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@WebServlet(name = "DeleteAdress", urlPatterns = {"/deleteadress"})
public class DeleteAdress extends HttpServlet {
    
    @EJB
    ClientService clientService;
    @EJB
    AdressService adressService;

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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        try ( PrintWriter out = response.getWriter()) {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            String idString = request.getParameter("id");
            int id = Integer.valueOf(idString);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Delete</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"\" method=\"POST\">");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\" />");
            out.println("<h1>Delete adress</h1>");
            out.println("<input type=\"submit\" value=\"deleteadress\" formaction=\"deleteadress\" formmethod=\"POST\" />");
            out.println("</form>");
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
        response.setContentType("text/html;charset=UTF-8");
        String idStr = Objects.toString(request.getParameter("id"), "");
        System.out.println(request.getParameter("id"));
        int id = Integer.valueOf(idStr);
        
        Adresses adress = adressService.findAdressById(id);
        Clients client = adress.getClientid();
        client.getAdressesSet().remove(adress);
        adressService.deleteAdress(adress);
        clientService.updateClient(client);
        response.sendRedirect("http://localhost:8080/J200_Lab1/viewlist");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
