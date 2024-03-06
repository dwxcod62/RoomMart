package com.codebrew.roommart.servlets.AdminServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import com.codebrew.roommart.dao.ProposeDao;
import com.codebrew.roommart.dto.Propose;

@WebServlet(name = "ManageProposeServlet", value = "/ManageProposeServlet")
public class ManageProposeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            List<Propose> proposeList = new ProposeDao().getAllPropose();
            request.setAttribute("proposeList", proposeList);
            session.setAttribute("CURRENT_PAGE", "propose");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("manage-propose-page").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}