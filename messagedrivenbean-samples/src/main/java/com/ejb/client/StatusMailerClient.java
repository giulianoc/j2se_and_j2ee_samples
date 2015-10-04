package com.ejb.client;

import com.ejb.sessions.OrderProcessingBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 18/03/15
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "StatusMailerClient", urlPatterns = {"/StatusMailerClient"})
public class StatusMailerClient extends HttpServlet {

    @EJB
    OrderProcessingBean orderProcessing;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StatusMailerClient</title>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>OrderProcessing session bean lookup to be done</h1>");
            out.println("<h1>Invoking SendOrderStatus() business method now</h1>");
            out.println("<h1>" + orderProcessing.SendOrderStatus() + "</h1>");
            out.println("<h1>Done !!!</h1>");

            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
