
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import utils.IpValidator;
import utils.MacValidator;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
@WebServlet(name = "CreateAdress", urlPatterns = {"/createadress"})
public class CreateAdress extends HttpServlet {
    
    @EJB
    ClientService clientService;
    @EJB
    AdressService adressService;
    @EJB
    IpValidator ipvalidator;
    @EJB
    MacValidator macvalidator;
    

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String idString = request.getParameter("id");
        int clientid = Integer.valueOf(idString);
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Clients table</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>Create new adress</h3>");
            out.println("<form action=\"createadress\" method=\"POST\">");
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + clientid + "\" />");
            out.println("<label for=\"ip\">IP-adress:</label>");
            out.print("<input type=\"text\" id=\"ip\" name=\"ip1\" maxlength=\"3\"/>");
            out.print(".");
            out.print("<input type=\"text\" name=\"ip2\" maxlength=\"3\"/>");
            out.print(".");
            out.print("<input type=\"text\" name=\"ip3\" maxlength=\"3\"/>");
            out.print(".");
            out.print("<input type=\"text\" name=\"ip4\" maxlength=\"3\"/>");
            out.println("</p>");
            out.println("<p>");
            out.println("<label for=\"mac\">MAC-adress:</label>");
            out.print("<input type=\"text\" id=\"mac\" name=\"mac1\" maxlength=\"2\"/>");
            out.print("-");
            out.print("<input type=\"text\" name=\"mac2\" maxlength=\"2\"/>");
            out.print("-");
            out.print("<input type=\"text\" name=\"mac3\" maxlength=\"2\"/>");
            out.print("-");
            out.print("<input type=\"text\" name=\"mac4\" maxlength=\"2\"/>");
            out.print("-");
            out.print("<input type=\"text\" name=\"mac5\" maxlength=\"2\"/>");
            out.print("-");
            out.print("<input type=\"text\" name=\"mac6\" maxlength=\"2\"/>");
            out.println("</p>");
            out.println("<p>");
            out.println("<label for=\"model\">Model of gear:</label>");
            out.print("<input type=\"text\" id=\"model\" name=\"model\" maxlength=\"100\"/>");
            out.println("</p>");
            out.println("<p>");
            out.println("<label for=\"loc\">Location:</label>");
            out.print("<input type=\"text\" id=\"loc\" name=\"locationadress\" maxlength=\"200\"/>");
            out.println("</p>");
            out.println("<p>");
            out.println("<input type=\"submit\" value=\"Create\" formaction=\"createadress\" formmethod=\"POST\" />");
            out.println("</p>");
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
        String idStr = request.getParameter("id");
        int clientid = Integer.parseInt(idStr);
        Clients client = clientService.findById(clientid);
        int adressid = adressService.getMaxId()+1;
        String ip = request.getParameter("ip1")
                + "." + request.getParameter("ip2")
                + "." + request.getParameter("ip3")
                + "." + request.getParameter("ip4");
        String mac = request.getParameter("mac1")
                + "-" + request.getParameter("mac2")
                + "-" + request.getParameter("mac3")
                + "-" + request.getParameter("mac4")
                + "-" + request.getParameter("mac5")
                + "-" + request.getParameter("mac6");
        String model = request.getParameter("model");
        String locationadress = request.getParameter("locationadress");
        if(ip.isEmpty()||!ipvalidator.isValid(ip)||!macvalidator.isValid(mac)){
            String message = "Вы неправильно ввели данные:\n"
                    +" IP-адрес должен состоять только из цифр и не может быть пустым\n"
                    + "MAC-адрес может состоять из латинских символов и цифр и не может быть пустым";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/error").forward(request, response);
        } else {
            Adresses adress = new Adresses(adressid, ip, mac, model, locationadress);
            adress.setClientid(client);
            client.getAdressesSet().add(adress);
            clientService.updateClient(clientService.findById(clientid));
            adressService.saveAdress(adress); 
            response.sendRedirect("http://localhost:8080/J200_Lab1/viewlist");
        }  
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
