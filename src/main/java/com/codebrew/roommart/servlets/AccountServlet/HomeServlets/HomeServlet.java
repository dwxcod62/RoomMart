package com.codebrew.roommart.servlets.AccountServlet.HomeServlets;

import com.codebrew.roommart.dao.RoomDAO;
import com.codebrew.roommart.dto.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/homeS")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String city = request.getParameter("city") == "" ? "all" : request.getParameter("city");
        String district = request.getParameter("district") == "" ? "all" : request.getParameter("district");
        String ward = request.getParameter("ward") == "" ? "all" : request.getParameter("ward");



            request.setAttribute("citySelected", city);

                request.setAttribute("districtSelected", district);

                    request.setAttribute("wardSelected", ward);



        System.out.println(city +": " + district + ": " + ward);
        RoomDAO rd = new RoomDAO();

        List<Room> rooms = rd.getListRoomsByCondition(city,district,ward);


        if (rooms.isEmpty()){
            System.out.println("empty list");
            request.setAttribute("rooms", null);
        }else {
            request.setAttribute("rooms", rooms);
        }
//        System.out.println("room id "+ rooms.get(0).getRoomId());
        request.getRequestDispatcher("home.jsp").forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}