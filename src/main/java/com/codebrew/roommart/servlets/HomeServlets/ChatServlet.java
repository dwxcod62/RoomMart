package com.codebrew.roommart.servlets.HomeServlets;

import com.codebrew.roommart.dao.HostelOwnerDAO;
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
        HostelOwnerDAO hod = new HostelOwnerDAO();
        Account acc = (Account) session.getAttribute("USER");

//        int renterId = acc.getAccId();
        int renterId = 3;

        String hostelId_raw = request.getParameter("hostelId");



        try {

//            int hostelId = Integer.parseInt(hostelId_raw);
            int hostelId = 1;

            int ownerId = hod.getOwnerIdByHostelId(hostelId);
            System.out.println("ownerid (session): "+ownerId);
            System.out.println("renterid (session): "+renterId);
            System.out.println("hostelid :"+hostelId);
            session.setAttribute("ownerId",ownerId);
            session.setAttribute("renterId",renterId);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("chatservlet error get");
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

    }
}