package com.codebrew.roommart.servlets.HomeServlets;

import com.codebrew.roommart.dao.InfrastructureDAO;
import com.codebrew.roommart.dao.RoomDAO;
import com.codebrew.roommart.dao.ServiceInfoDAO;
import com.codebrew.roommart.dto.InfrastructureItem;
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
import com.cloudinary.*;
import com.codebrew.roommart.dto.ServiceInfo;
import com.codebrew.roommart.utils.EncodeUtils;

@WebServlet(name = "RoomDetailServlet", value = "/RoomDetailServlet")
public class RoomDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("RoomDetailServlet==============================================================");
        String decodeRoomId = null;
        String rid_raw = null;
        try{
            decodeRoomId = EncodeUtils.decodeString(request.getParameter("rid"));
            System.out.println("decodeRoomId: " +decodeRoomId);
             rid_raw = decodeRoomId;
        }catch (Exception e){
            System.out.println("RoomDetail Servlet error - decode id");
            request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);
            return;
        }

        int rid=0;
        try {
                   rid = Integer.parseInt(rid_raw);
        }catch (Exception e){
            System.out.println("RoomDetail Servlet error - Parse int id error");
            request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);

           return;

        }

        RoomDAO rd = new RoomDAO();
        InfrastructureDAO infraDao = new InfrastructureDAO();
        ServiceInfoDAO serviceIDao = new ServiceInfoDAO();

        Room r = rd.getRoomInformationByRoomId(rid);

        List<Room> recommendRoom = rd.getAllRecommendRoom(rid);


        if (r == null){
            System.out.println("ROOM DETAIL EMPTY");
        }else{

            RoomInformation ri = r.getRoomInformation();
            List<ServiceInfo> list_service = serviceIDao.getServicesOfHostel(r.getHostelId());
            List<InfrastructureItem> list_infras = infraDao.getAllInfrastructure();
            request.setAttribute("room",r);
            request.setAttribute("roomImg",r.getImgUrl());
            request.setAttribute("roomInfor",ri);
            request.setAttribute("list",recommendRoom);
            request.setAttribute("serviceList",list_service);
            request.setAttribute("infrasList",list_infras);

        }

        request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}