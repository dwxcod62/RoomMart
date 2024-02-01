package com.codebrew.roommart.servlets.HomeServlets;

import com.codebrew.roommart.dto.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChatServlet", value = "/ChatServlet")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("USER");

        int renterId = acc.getAccId();
        String hostelId_raw = request.getParameter("hostelId");

        try {

            int hostelId = Integer.parseInt(hostelId_raw);

            session.setAttribute("hostelId",hostelId);
            session.setAttribute("renterId",renterId);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("chat error get");
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

    }
}