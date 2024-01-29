package com.codebrew.roommart.servlets.HomeServlets;

import com.codebrew.roommart.dao.RoomDAO;
import com.codebrew.roommart.dto.Room;

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

        RoomDAO rd = new RoomDAO();

        List<Room> rooms = rd.getAllRoom();


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

        request.setCharacterEncoding("UTF-8");
        String city = request.getParameter("city") == "" ? "all" : request.getParameter("city");

        String district = request.getParameter("district") == "" ? "all" : request.getParameter("district");
        String ward = request.getParameter("ward") == "" ? "all" : request.getParameter("ward");
        String inputText = request.getParameter("textInput").trim();


        request.setAttribute("citySelected", city);

        request.setAttribute("districtSelected", district);

        request.setAttribute("wardSelected", ward);



        System.out.println(city +" city : " + district + " district : " + ward+" ward");

        RoomDAO rd = new RoomDAO();
        List<Room> rooms = new ArrayList<>();
        if(inputText!=null){
            rooms = rd.getListRoomsByCondition(city,district,ward, inputText);
        }else {
            rooms = rd.getAllRoom();
        }



        if (rooms.isEmpty()){
            System.out.println("EMPTY LIST");
            request.setAttribute("rooms", null);
        }else {
            request.setAttribute("rooms", rooms);
        }
//        System.out.println("room id "+ rooms.get(0).getRoomId());
        request.getRequestDispatcher("home.jsp").forward(request,response);
    }
}