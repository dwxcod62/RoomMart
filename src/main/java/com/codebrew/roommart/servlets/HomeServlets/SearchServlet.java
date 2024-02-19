package com.codebrew.roommart.servlets.HomeServlets;

import com.codebrew.roommart.dao.RoomDAO;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String city = request.getParameter("city") == "" ? "all" : request.getParameter("city");

        String district = request.getParameter("district") == "" ? "all" : request.getParameter("district");
        String ward = request.getParameter("ward") == "" ? "all" : request.getParameter("ward");
        String inputText = request.getParameter("key").trim();

        request.setAttribute("citySelected", city);

        request.setAttribute("districtSelected", district);

        request.setAttribute("wardSelected", ward);

        System.out.println(city +" city : " + district + " district : " + ward+" ward");

        RoomDAO rd = new RoomDAO();
        List<Room> rooms = new ArrayList<>();
        if(inputText!=null){
            System.out.println("get input text: " + inputText);
            rooms = rd.getListRoomsByCondition(city,district,ward, inputText);
            System.out.println("get size room by condition: "+rooms.size());
        }else {
            rooms = rd.getAllRoom();
            System.out.println("get size room: "+rooms.size());
        }



        if (rooms.isEmpty()){
            System.out.println("EMPTY LIST");
            request.setAttribute("rooms", null);
        }else {
            request.setAttribute("rooms", rooms);
        }

        request.setAttribute("key",inputText);

        request.getRequestDispatcher("home.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}