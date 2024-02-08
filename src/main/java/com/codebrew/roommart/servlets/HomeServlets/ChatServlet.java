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
        String renterId = request.getParameter("renterId");
        int role = 1;
        int ownerId=0;
        if(role==1){
            ownerId = 2; // get from acc id
        }



        try {

            System.out.println("ownerid (session): "+ownerId);
            System.out.println("renterid (session): "+renterId);
            System.out.println("role: "+role);


            session.setAttribute("ownerId",ownerId);
            session.setAttribute("renterId",renterId);
            session.setAttribute("role",role);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("chatservlet error get");
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        HostelOwnerDAO hod = new HostelOwnerDAO();
        Account acc = (Account) session.getAttribute("USER");

//        int renterId = acc.getAccId();
        int renterId = 3;
    int role = 2;
        String hostelId_raw = request.getParameter("hostelId");
        String roomId_raw = request.getParameter("roomId");

        try {

            int hostelId = Integer.parseInt(hostelId_raw);
            int roomId = Integer.parseInt(roomId_raw);

            int ownerId = hod.getOwnerIdByHostelId(hostelId);
            System.out.println("ownerid (session): "+ownerId);
            System.out.println("renterid (session): "+renterId);
            System.out.println("hostelid :"+hostelId);

            request.setAttribute("hostelId",hostelId);
            request.setAttribute("roomId",roomId);
            session.setAttribute("role",role);
            session.setAttribute("ownerId",ownerId);
            session.setAttribute("renterId",renterId);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("chatservlet error get");
            System.out.println(e);
        }

    }
}