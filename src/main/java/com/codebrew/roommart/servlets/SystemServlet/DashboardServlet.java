package com.codebrew.roommart.servlets.SystemServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static String url = "login-page";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                dashboard(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "DashboardServlet.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response );
    }

    private void dashboard(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession(true);
            Account account = (Account) session.getAttribute("USER");
            String email = account.getAccountInfo().getInformation().getEmail();
            AccountDao ad = new AccountDao();
            System.out.println("check renter has room");
            int checkRenterHasRoom = ad.getRoomOfRenter(email);

            if (account != null) {
                switch (account.getRole()) {
                    case 0:
                        url = "AdminDashboard";
                        break;
                    case 1:
                        session.setAttribute("st",0);
                        url = "owner-dashboard";
                        break;
                    case 2:
                        session.setAttribute("st",checkRenterHasRoom);
                        if (checkRenterHasRoom==0){
                            url = "home";
                        }else { url = "RenterHome";}

                        break;
                }
            }

            session.setAttribute("CURRENT_PAGE", "dashboard");
        } catch (Exception e) {
            log("Error at DashboardServlet: " + e.toString());
        } finally {
            req.getRequestDispatcher(url).forward(req, res);
        }
    }

}