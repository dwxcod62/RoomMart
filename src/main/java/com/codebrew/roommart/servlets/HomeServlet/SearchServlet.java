package com.codebrew.roommart.servlets.HomeServlet;


import com.codebrew.roommart.dao.RoomDao;
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

        request.setAttribute("key",inputText);

        int page =request.getParameter("page")!=null? Integer.parseInt(request.getParameter("page")):1;

        System.out.println(city +" city : " + district + " district : " + ward+" ward");

        RoomDao rd = new RoomDao();
        List<Room> rooms = new ArrayList<>();

        System.out.println("get input text: " + inputText);
        rooms = rd.getListRoomsByCondition(city,district,ward, inputText,page,12,0,0);
        int total = rd.getTotalRoomsByCondition(city,district,ward,inputText);
        request.setAttribute("total", Math.ceil((double) total / 12));
        request.setAttribute("page", page);

        System.out.println("-> get total in condition: " + total);

        System.out.println("->get size room by condition: "+rooms.size());





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