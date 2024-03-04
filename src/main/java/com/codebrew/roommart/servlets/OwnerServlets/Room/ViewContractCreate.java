//package com.codebrew.roommart.servlets.OwnerServlets.Room;
//
//import com.codebrew.roommart.dto.Room;
//import com.codebrew.roommart.dto.Status;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebServlet(name = "ViewContractCreate", value = "/ViewContractCreate")
//public class ViewContractCreate extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//
//        String url = "error";
//
//        Room room = (Room) session.getAttribute("room");
//
//        if (room != null){
//            url = "create-contract-page";
//        } else {
//            url = "owner-get-room-list";
//            Status status = Status.builder()
//                    .status(false)
//                    .content("Something wrong, try again!").build();
//
//            request.setAttribute("RESPONSE_MSG", status);
//        }
//        request.getRequestDispatcher(url).forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}