package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.InvoiceDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Invoice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetRenterInvoiceServlet", value = "/GetRenterInvoiceServlet")
public class GetRenterInvoiceServlet extends HttpServlet {
    private static final String SUCCESS = "/pages/renter/renter-invoice-list.jsp";
    private static final String ERROR = "/pages/renter/renter-invoice-list.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            int accID = account.getAccId();

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            List<Invoice> invoiceList = invoiceDAO.getInvoiceListByRenterID(accID);
            request.setAttribute("uri", request.getRequestURI());
            if (invoiceList.size() > 0){
                request.setAttribute("INVOICE_LIST", invoiceList);
                url = SUCCESS;
            }

        }catch (Exception e){
            log("Error at GetRenterInvoiceServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }

}