package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Information;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetRenterInfoServlet", value = "/GetRenterInfoServlet")
public class GetRenterInfoServlet extends HttpServlet {
    public static final String ERROR = "renter-Profile";
    public static final String SUCCESS = "renter-Profile";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        Information accInfo;
        try {
            request.setAttribute("uri", request.getRequestURI());
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int renterId = acc.getAccId();

            accInfo = new InformationDao().getAccountInformationById(renterId);
            if (accInfo != null){
                request.setAttribute("ACC_INFO", accInfo);
                url = SUCCESS;
            }
        }catch (Exception e){
            log("Error at GetRenterInfoServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}