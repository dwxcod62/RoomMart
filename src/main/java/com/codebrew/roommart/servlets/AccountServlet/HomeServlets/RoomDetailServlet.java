package com.codebrew.roommart.servlets.AccountServlet.HomeServlets;

import com.codebrew.roommart.dao.RoomDAO;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "RoomDetailServlet", value = "/roomdetail")
public class RoomDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RoomDetailServlet");
        String rid_raw = request.getParameter("rid");
        int rid = Integer.parseInt(rid_raw);
        RoomDAO rd = new RoomDAO();
        Room r = rd.getRoomInformationByRoomId(rid);
        List<Room> recommendRoom = new ArrayList<>();


        for (int i = 1; i<=5;i++){
            System.out.println("reccomend room");
            if(i!=rid){recommendRoom.add(rd.getRoomInformationByRoomId(i));}

        }

        if (r == null){
            System.out.println("room empty");
        }else{

            RoomInformation ri = r.getRoomInformation();
            request.setAttribute("room",r);
            request.setAttribute("roomImg",r.getImgUrl());
            request.setAttribute("roomInfor",ri);
            request.setAttribute("list",recommendRoom);

        }

        request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}