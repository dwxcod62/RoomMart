package com.codebrew.roommart.servlets.SystemServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    private static String url = "login";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("USER");

            if (acc != null) {
                switch (acc.getRole()) {
                    case 0: //  Admin
                        url = "thieu.jsp";
                        break;
                    case 1: //  Owner
                        url = "owner-dashboard";
                        break;
                    case 2: //  Staff
                        url = "thieu.jsp";
                        break;
                    case 3: //  Renter
                        SystemDao dao = new SystemDao();
                        boolean st = dao.isRenterRentingRoom(acc.getAccId());
                        session.setAttribute("st",st);
                        if (st){
                            url = "Renter-HomePage";
                        } else {
                            url = "home";
                        }
                }
            }

        } catch ( Exception e){
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}