package com.codebrew.roommart.servlets.HomeServlet;





import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.HostelOwnerDao;
import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChatServlet", value = "/ChatServlet")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("ChatServlet======================================================");
        System.out.println("owner");
        HttpSession session = request.getSession();
        HostelOwnerDao hod = new HostelOwnerDao();
        Account acc = (Account) session.getAttribute("USER");
        AccountDao uid = new AccountDao();

        AccountInfo ui = uid.getAccountInformationById(acc.getAccId());




        String renterId_raw = request.getParameter("renterId");
        if (renterId_raw != null){
            System.out.println("renterId_raw " +renterId_raw);
            int renterId = Integer.parseInt(renterId_raw);
            AccountInfo renter_ui = uid.getAccountInformationById(renterId);
            request.setAttribute("infor2",renter_ui.getInformation());

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

            request.setAttribute("infor",ui.getInformation());


            session.setAttribute("ownerId",ownerId);
            session.setAttribute("renterId",renterId_raw);
            session.setAttribute("role",role);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("ChatServlet error - get");
        }



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("renter");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        HostelOwnerDao hod = new HostelOwnerDao();
        Account acc = (Account) session.getAttribute("USER");
        AccountDao uid = new AccountDao();

        AccountInfo ui = uid.getAccountInformationById(acc.getAccId());


//        int renterId = acc.getAccId();
        int renterId = acc.getAccId();
        int role = acc.getRole();
        System.out.println("role servlet:" + role);
        String hostelId_raw = request.getParameter("hostelId");
        String roomId_raw = request.getParameter("roomId");

        try {

            int hostelId = Integer.parseInt(hostelId_raw);
            int roomId = Integer.parseInt(roomId_raw);


            int ownerId = hod.getOwnerIdByHostelId(hostelId);
            AccountInfo owner_ui = uid.getAccountInformationById(ownerId);
            Information owner_infor = owner_ui.getInformation();
            request.setAttribute("infor2",owner_infor);
            System.out.println("ownerid (session): "+ownerId);
            System.out.println("renterid (session): "+renterId);
            System.out.println("hostelid :"+hostelId);



            Information acc_infor = ui.getInformation();
            request.setAttribute("infor",acc_infor);
            request.setAttribute("hostelId",hostelId);
            request.setAttribute("roomId",roomId);
            session.setAttribute("role",role);
            session.setAttribute("ownerId",ownerId);
            session.setAttribute("renterId",renterId);
            request.getRequestDispatcher("pages/home/chat.jsp").forward(request,response);

        }catch (Exception e){
            System.out.println("ChatServlet error - post");
            System.out.println(e);
        }

    }
}
