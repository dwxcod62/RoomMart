//package com.codebrew.roommart.servlets.OwnerServlets.Hostel;
//
//import com.codebrew.roommart.dao.RoomDAO;
//import com.codebrew.roommart.dto.OwnerDTO.Hostel;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebServlet(name = "DeleteRoomServlet", value = "/DeleteRoomServlet")
//public class DeleteRoomServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//                int roomID = Integer.parseInt(request.getParameter("rid"));
//        System.out.println("delete room");
//        HttpSession session = request.getSession();
//        int hostelID = ((Hostel) session.getAttribute("hostel")).getHostelID();
//        String url = "detailHostel?hostelID="+hostelID;
//        RoomDAO roomDao = new RoomDAO();
//        try {
//            roomDao.deleteRoom(roomID);
//
//        }catch (Exception e){
//            System.out.println("delete room error");
//        }
//        response.sendRedirect(url);
//    }
//}