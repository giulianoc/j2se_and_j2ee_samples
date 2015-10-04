package com.ejb.client;

import com.ejb.credit.CreditCheckEndpointBean;
import com.ejb.credit.CreditService;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 18/03/15
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "CreditServiceClient", urlPatterns = {"/CreditServiceClient"})
public class CreditServiceClient extends HttpServlet {

    // the WSDL URL will be: http://127.0.0.1:8080/webservices-samples/CreditService/CreditCheckEndpointBean?WSDL
    // command to generate the java stub: ~/ThirdPartySoftware/linux_unix/jboss/wildfly-8.2.0.Final/bin/wsconsume.sh -k --package=com.ejb.credit -n http://127.0.0.1:8080/webservices-samples/CreditService/CreditCheckEndpointBean?WSDL

    @WebServiceRef(wsdlLocation = "http://127.0.0.1:8080/webservices-samples/CreditService/CreditCheckEndpointBean?WSDL")
    CreditService service;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreditServiceClient</title>");
            out.println("</head>");
            out.println("<body>");
            CreditCheckEndpointBean creditService = service.getCreditCheckEndpointBeanPort();
            out.println("<h1>Credit Check returned: " + creditService.creditCheck("12345678") + "</h1>");
            // out.println("<h1>Credit Check returned: </h1>");
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
