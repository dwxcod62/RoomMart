package com.codebrew.roommart.servlets.HomeServlets;




import com.codebrew.roommart.dao.HostelOwnerDAO;
import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;

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
        UserInformationDAO uid = new UserInformationDAO();

        UserInformation ui = uid.getAccountInformationById(acc.getAccId());

//        int renterId = acc.getAccId();


        String renterId_raw = request.getParameter("renterId");
        if (renterId_raw != null){
            System.out.println("renterId_raw " +renterId_raw);
            int renterId = Integer.parseInt(renterId_raw);
            UserInformation renter_ui = uid.getAccountInformationById(renterId);
            request.setAttribute("infor2",renter_ui);

        }

        int role = acc.getRole();
        int ownerId=0;
        if(role==1){
            ownerId = acc.getAccId(); // get from acc id
        }

        System.out.println("est");

        try {

            System.out.println("ownerid (session): "+ownerId);
            System.out.println("renterid (session): "+renterId_raw);
            System.out.println("role: "+role);

            request.setAttribute("infor",ui);


            session.setAttribute("ownerId",ownerId);
            session.setAttribute("renterId",renterId_raw);
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
        UserInformationDAO uid = new UserInformationDAO();

        UserInformation ui = uid.getAccountInformationById(acc.getAccId());

//        int renterId = acc.getAccId();
        int renterId = acc.getAccId();
    int role = acc.getRole();
        String hostelId_raw = request.getParameter("hostelId");
        String roomId_raw = request.getParameter("roomId");

        try {

            int hostelId = Integer.parseInt(hostelId_raw);
            int roomId = Integer.parseInt(roomId_raw);

            int ownerId = hod.getOwnerIdByHostelId(hostelId);
            UserInformation owner_ui = uid.getAccountInformationById(ownerId);
            request.setAttribute("infor2",owner_ui);
            System.out.println("ownerid (session): "+ownerId);
            System.out.println("renterid (session): "+renterId);
            System.out.println("hostelid :"+hostelId);

            request.setAttribute("infor",ui);
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