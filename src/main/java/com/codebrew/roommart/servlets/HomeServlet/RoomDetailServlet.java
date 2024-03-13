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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
            String status = request.getParameter("sts");
            if (status != null){
                if (status.equals("0")){
                    HandlerStatus status1 = HandlerStatus.builder()
                            .status(true)
                            .content("Có lỗi xảy ra").build();
                    request.setAttribute("RESPONSE_MSG1", status1);
                } else {
                    HandlerStatus status1 = HandlerStatus.builder()
                            .status(true)
                            .content("Thành công! sau khi chủ trọ xác nhận sẽ gửi thông báo về mail của bạn!").build();
                    request.setAttribute("RESPONSE_MSG1", status1);
                }
            }

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
//        System.out.println("end date: " + endDate);
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
        rd.addView(r.getRoomId());
        Account accRenter = (Account) session.getAttribute("USER");
        if (accRenter!= null && accRenter.getRole()==2){
            rd.updateRecentlyRoomId(r.getRoomId(),accRenter.getAccId());
        }
        Account accRent2 = (Account) session.getAttribute("USER");
        Account accRent = (Account) new AccountDao().getAccountById(accRent2.getAccId());
//        System.out.println("--> check acc user: "+ accRent);
        if (accRent != null){

            java.util.Date currentDate = new java.util.Date();

            // Định dạng ngày thành chuỗi
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                // Chuyển chuỗi createDate thành đối tượng Date
                java.util.Date createDateObj = formatter.parse(accRent.getCreateDate());
                java.util.Date expiration;
                if (accRent.getExpiredDate() == null){
                    expiration = new java.util.Date();
                }else
                    expiration = formatter.parse(accRent.getExpiredDate());
                System.out.println(expiration);

                // So sánh ngày hiện tại với ngày từ createDate
                //check tai khoan tao hon 1 ngay chua
                if (currentDate.after(createDateObj)) {
                    //check lan cuoi dang nhap da hon 1 ngay chua

                    Calendar currentCalendar = Calendar.getInstance();
                    currentCalendar.setTime(currentDate);
                    currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    currentCalendar.set(Calendar.MINUTE, 0);
                    currentCalendar.set(Calendar.SECOND, 0);
                    currentDate = currentCalendar.getTime();

                    Calendar expirationCalendar = Calendar.getInstance();
                    expirationCalendar.setTime(expiration);
                    expirationCalendar.set(Calendar.HOUR_OF_DAY, 0);
                    expirationCalendar.set(Calendar.MINUTE, 0);
                    expirationCalendar.set(Calendar.SECOND, 0);
                    expiration = expirationCalendar.getTime();

                    String currentDateStr = formatter.format(currentDate);
                    String expirationStr = formatter.format(expiration);
                    System.out.println("expiration: "+expirationStr);
                    System.out.println("currentDate: "+currentDateStr);
                    int comparisonResult = currentDateStr.compareTo(expirationStr);
                    if (comparisonResult > 0){

                        System.out.println("currentDate.after(expiration):"+(comparisonResult));
                        rd.updateExpiredDateRoomId(accRent.getAccId());
                     request.setAttribute("ads",true);


                    }else {
                        request.setAttribute("ads",false);
                    }
                }
            } catch (ParseException e) {
                System.out.println(e);
            }
        }else {
            request.setAttribute("ads",true);
        }
        request.getRequestDispatcher("pages/home/roomdetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}