package com.codebrew.roommart.servlets.OwnerServlet.Hostel;

import com.codebrew.roommart.dao.RoomDAO;

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
        RoomDAO roomDao = new RoomDAO();
        try {
            roomDao.deleteRoom(roomID);

        }catch (Exception e){
            System.out.println("delete room error");
        }
    }
}