package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                logout(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "LogoutServlet.doPost");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String url = "success";
        AccountDao dao = new AccountDao();

        try {
            HttpSession session = request.getSession(true);
            Account acc = (Account) session.getAttribute("USER");
            String username = acc.getUsername();

            System.out.println("- " + username + " logout");
            session.invalidate();
            Cookie[] cookie = request.getCookies();

            for (Cookie c : cookie) {
                if (c.getName().equals("selector")) {
                    c.setMaxAge(0);
                    c.setValue(null);
                    response.addCookie(c);
                    dao.updateTokenByUserName(null, username);
                }
            }
            url = "login-page";

        } catch (Exception e) {
            log("Error at LogoutServlet: " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }
}