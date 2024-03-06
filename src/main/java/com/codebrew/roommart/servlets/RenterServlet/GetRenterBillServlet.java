package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.BillDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetRenterBillServlet", value = "/GetRenterBillServlet")
public class GetRenterBillServlet extends HttpServlet {
    private static final String SUCCESS = "renter-Bill";
    private static final String ERROR = "renter-Bill";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                Load_Renter_Invoice_List(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "GetRenterBillServlet");
    }

    protected void Load_Renter_Invoice_List(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            int accID = account.getAccId();

            BillDao billDAO = new BillDao();
            List<Bill> billList = billDAO.getBllListByRenterID(accID);
            request.setAttribute("uri", request.getRequestURI());
            if (billList.size() > 0){
                request.setAttribute("BILL_LIST", billList);
                url = SUCCESS;
            }

        }catch (Exception e){
            log("Error at GetRenterBillServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}