package com.ejb.sessionClients;

import com.ejb.sessions.SearchFacadeBean;
import com.ejb.sessions.ShopperCountBean;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 17/03/15
 * Time: 09:55
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "SearchFacadeClient", urlPatterns = {"/SearchFacadeClient"})
public class SearchFacadeClient extends HttpServlet {

    @EJB
    SearchFacadeBean searchFacade;

    @EJB
    ShopperCountBean shopperCount;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setContentType("text/html;chatset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchFacadeClient</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Starting search Facade test ... </h1>");

            out.println("<h1>SearchFacade lookup</h1>");
            out.println("<h1>Searching wine</h1>");

            List wineList = searchFacade.wineSearch("Red");
            out.println("<h1>Printing wine list</h1>");

            for(String wine: (List<String>)wineList)
            {
                out.println("<h1>" + wine + "</h1>");
            }

            System.out.println("Printing shopper count after incrementing it ...");
            shopperCount.incrementShopperCount();
            out.println("<h1>" + shopperCount.getShopperCount() + "</h1>");

            out.println("</body>");
            out.println("</html>");
        }
        finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
