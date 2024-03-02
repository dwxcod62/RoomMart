package com.codebrew.roommart.servlets.OwnerServlets;



import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRoomServlet", value = "/DeleteRoomServlet")
public class DeleteRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roomID = Integer.parseInt(request.getParameter("rid"));
        System.out.println("delete room");
        HttpSession session = request.getSession();
        int hostelID = ((Hostel) session.getAttribute("hostel")).getHostelID();
        String url = "detailHostel?hostelID="+hostelID;
        RoomDao roomDao = new RoomDao();
        try {
            roomDao.deleteRoom(roomID);

        }catch (Exception e){
            System.out.println("delete room error");
        }
        response.sendRedirect(url);
    }
}