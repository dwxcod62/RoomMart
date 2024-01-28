package com.codebrew.roommart.servlets.AccountServlet.HomeServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChatServlet", value = "/chatS")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hostelId_raw = request.getParameter("hostelID");
        String renterId_raw = request.getParameter("retnerID");

        try {
            int hostelId = Integer.parseInt(hostelId_raw);
            int renterId = Integer.parseInt(renterId_raw);

            request.setAttribute("hostelId",hostelId);
            request.setAttribute("renterId",renterId);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("chat error get");
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}