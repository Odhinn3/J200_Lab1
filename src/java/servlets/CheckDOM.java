/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Adresses;
import models.Clients;
import parsers.Parser;

/**
 *
 * @author A.Konnov <github.com/Odhinn3>
 */
public class CheckDOM extends HttpServlet {
    
    @EJB
    private Parser parser;

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String text = request.getParameter("search");
        List<Clients> clients = filterList(parser.readDomXml(), text);
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Clients table from XML by DOM</title> <meta charset=\"UTF-8\">");
//            out.println("<link href=\"URL_адрес_CSS_файла\" rel=\"stylesheet\" type=\"text/css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<form action=\"checkdom\" method=\"GET\">");
            out.println("<input type=\"text\" name=\"search\" value=\"" + text + "\" />");
            out.println("<input type=\"submit\" value=\"Search\" /><br><br>");
            out.println("</form>");
            out.println("<div>");
            out.println("<table border=\"2\">");
            out.println("<caption><b>CheckDOM Clients</b></caption>");   
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th colspan=\"7\" >Clients</th>");
            out.println("<th colspan=\"7\" >Network gears</th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th colspan=\"3\" ></th>");
            out.println("<th>id</th>");
            out.println("<th>clientname</th>");
            out.println("<th>clienttype</th>");
            out.println("<th>regdate</th>");
            out.println("<th>adressid</th>");
            out.println("<th>ip</th>");
            out.println("<th>mac</th>");
            out.println("<th>model</th>");
            out.println("<th>localaddress</th>");
            out.println("<th></th>");
            out.println("</tr>");
            out.println("</thead>");
            out.println("</div>");
            out.println("<tbody>");
            if(!clients.isEmpty()){
                for(Clients client : clients){
                    Integer id = client.getClientid();
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    int size = client.getAdressesSet().size();
                    int rowspan = 0;
                    if(size!=0){
                        rowspan = size;
                    } else {
                        rowspan = 1;
                    }
                    out.println("<tr>");
                    out.println("<td rowspan=" + rowspan + ">" +  "-" + "</td>");
                    out.println("<td rowspan=" + rowspan + ">" +  "-" + "</td>");
                    out.println("<td rowspan=" + rowspan + ">" +  "-" + "</td>");
                    out.println("<td rowspan=" + rowspan + ">" + id + "</td>");
                    out.println("<td rowspan=" + rowspan + ">" + client.getClientname() + "</td>");
                    out.println("<td rowspan=" + rowspan + ">" + client.getClienttype() + "</td>");
                    out.println("<td rowspan=" + rowspan + ">" + sdf.format(client.getRegdate()) + "</td>");
                    if(!client.getAdressesSet().isEmpty()){
                        for(Adresses adress : client.getAdressesSet()){
                            int adressid = adress.getAdressid();
                            out.println("<td>" + adressid + "</td>");
                            out.println("<td>" + adress.getIp() + "</td>");
                            out.println("<td>" + adress.getMac() + "</td>");
                            out.println("<td>" + adress.getModel() + "</td>");
                            out.println("<td>" + adress.getLocationadress() + "</td>");
                            out.println("<td>" + "-" + "</td>");
                            out.println("<td>" + "-" + "</td>");
                            out.println("</tr>");
                        } 
                    } else {
                        out.println("<td colspan = \"6\"></td>");
                        out.println("</tr>");
                    } 
                    out.println("</tr>");
                    out.println("</div>");
                } 
            }
            out.println("</tbody>");
            out.println("</table>");  
            out.println("</body>");
            out.println("</html>");
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
    
    private List<Clients> filterList(List<Clients> clients, String text){
        if(clients==null||clients.isEmpty()||text==null||text.isEmpty()){
            return clients;
            }
            System.out.println("filtering coll");
            clients = clients.stream().filter(e ->
                    (e.getClientname().toLowerCase().contains(text.toLowerCase())) ||
                    (e.getClienttype().toLowerCase().contains(text.toLowerCase()))
            ).collect(Collectors.toList());
        return clients;     
    }

}
