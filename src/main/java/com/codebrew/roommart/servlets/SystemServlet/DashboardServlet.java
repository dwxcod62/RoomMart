package com.codebrew.roommart.servlets.SystemServlet;

import com.codebrew.roommart.dto.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashboardServlet", value = "/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "login";
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("USER");
        if (acc != null){
          if (acc.getRole() == 0){ //  Admin
              url = "thieu.jsp";
          } else if (acc.getRole() == 1) { // Owner
              url = "thieu.jsp";
          } else if (acc.getRole() == 2 ){ // Staff
              url = "thieu.jsp";
          } else if (acc.getRole() == 3) { // Renter
              url = "Renter-HomePage";
          }
        }
        response.sendRedirect(url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}