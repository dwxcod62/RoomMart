package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogOutServlet", value = "/LogOutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "success";
        AccountDao dao = new AccountDao();
        try {
            HttpSession session = request.getSession(true);
            Account acc = (Account) session.getAttribute("USER");
            String username = acc.getEmail();

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

            url = "login";
        }catch (Exception e) {
            log("Error at LogoutServlet: " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            logout(request, response);
            return null;
        }, "LogoutServlet");
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("User " + ((Account) session.getAttribute("USER")).getEmail() + " dang xuat!");
            session.invalidate();
        }
        try{
            response.sendRedirect("login");
        } catch ( Exception e){
            e.printStackTrace();
        }

    }
}