package com.codebrew.roommart.servlets.AdminServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import com.codebrew.roommart.dao.InfrastructureDao;
import com.codebrew.roommart.dto.InfrastructureItem;


@WebServlet(name = "ManageInfrastructureServlet", value = "/ManageInfrastructureServlet")
public class ManageInfrastructureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("CURRENT_PAGE", "infrastructure");
            List<InfrastructureItem> infrastructuresList = new InfrastructureDao().getAllInfrastructure();
            request.setAttribute("infrastructuresList", infrastructuresList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("manage-infrastructure-page").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
