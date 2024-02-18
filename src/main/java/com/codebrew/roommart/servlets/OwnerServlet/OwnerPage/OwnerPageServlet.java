package com.codebrew.roommart.servlets.OwnerServlet.OwnerPage;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OwnerPageServlet", value = "/ownerPage")
public class OwnerPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("CURRENT_PAGE", "dashboard");
        request.getRequestDispatcher("pages/owner/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
