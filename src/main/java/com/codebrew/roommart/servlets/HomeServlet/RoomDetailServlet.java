package com.codebrew.roommart.servlets.HomeServlet;


import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.cloudinary.*;
import com.codebrew.roommart.utils.EncodeUtils;

@WebServlet(name = "RoomDetailServlet", value = "/RoomDetailServlet")
public class RoomDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("RoomDetailServlet==============================================================");
        String decodeRoomId = null;
        String decodeHostelId = null;
        String rid_raw = null;
        String hostelId_raw = null;
        Date endDate = null;
        HttpSession session = request.getSession();
        session.setAttribute("CURRENT_PAGE","home-room");
        try{
            decodeRoomId = EncodeUtils.decodeString(request.getParameter("rid"));
            decodeHostelId = EncodeUtils.decodeString(request.getParameter("hostelId"));
            System.out.println("decodeRoomId: " +decodeRoomId);
            System.out.println("decodeHostelId: " +decodeHostelId);
            rid_raw = decodeRoomId;
            hostelId_raw = decodeHostelId;
        }catch (Exception e){
            System.out.println("RoomDetail Servlet error - decode id");
            request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);
            return;
        }

        int rid=0;
        int hostelId=0;
        try {
            rid = Integer.parseInt(rid_raw);
            hostelId = Integer.parseInt(hostelId_raw);


        }catch (Exception e){
            System.out.println("RoomDetail Servlet error - Parse int id error");
            request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);

            return;

        }
        //call dao
        RoomDao rd = new RoomDao();
        endDate = rd.get_end_date_by_RoomId(rid); // end date
        System.out.println("end date: " + endDate);
        InformationDao ud = new InformationDao();
        boolean isSuccess = false;
        InfrastructureDao infraDao = new InfrastructureDao();
        ServiceInfoDAO serviceIDao = new ServiceInfoDAO();

        Room r = rd.getRoomInformationByRoomId(rid);
        Information owner_hostel_info = null;
        try {
            owner_hostel_info = ud.getHostelOwnerInfoByHostelId(hostelId);
        } catch (SQLException e) {
            System.out.println("getHostelOwnerInfoByHostelId error");
        }
        List<Room> recommendRoom = rd.getAllRecommendRoom(rid);

        if (!recommendRoom.isEmpty()){
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

// check room empty ?

        if (r == null){
            System.out.println("ROOM DETAIL EMPTY");
        }else{
            // get services and infrastures of room
            RoomInformation ri = r.getRoomInformation();
            List<ServiceInfo> list_service = serviceIDao.getServicesOfHostel(r.getHostelId());
            List<Infrastructures> list_infras = infraDao.getRoomInfrastructures(rid);




            if (list_infras.isEmpty()){
                System.out.println("list_infras is empty -> call get all infrastures");
                List<InfrastructureItem> list_infras_i = infraDao.getAllInfrastructure();
                request.setAttribute("infrasListItem",list_infras_i);
            }
            request.setAttribute("endDate",endDate);
            request.setAttribute("ownerAcc",owner_hostel_info);
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