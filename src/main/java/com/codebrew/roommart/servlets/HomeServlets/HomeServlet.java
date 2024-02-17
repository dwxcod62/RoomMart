package com.codebrew.roommart.servlets.HomeServlets;

import com.codebrew.roommart.dao.RoomDAO;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("HomeServlet============================================");
        RoomDAO rd = new RoomDAO();


        List<Room> rooms = rd.getAllRoom();
        System.out.println("get size room: "+rooms.size());
        boolean isSuccess = false;
        if (!rooms.isEmpty()){
            isSuccess=true;
        }
        if (isSuccess) {
            request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                    .status(true)
                    .content("Loading room successfully").build());
        } else {
            request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                    .status(false)
                    .content("Loading room fail!").build());
        }

        if (rooms.isEmpty()){
            System.out.println("empty list");
            request.setAttribute("rooms", null);
        }else {
            request.setAttribute("rooms", rooms);
        }

        request.getRequestDispatcher("home.jsp").forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}