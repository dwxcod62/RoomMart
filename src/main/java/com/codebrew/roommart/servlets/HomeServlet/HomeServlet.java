package com.codebrew.roommart.servlets.HomeServlet;

import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "HomeServlet", value = "/HomeServlet")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("HomeServlet============================================");
        RoomDao rd = new RoomDao();
        HostelDao htd = new HostelDao();

        try {
            List<Hostel> listHostel = htd.getAllHostel();
            request.setAttribute("listHostel",listHostel);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Integer> listRoomArea = rd.getRoomArea();
        request.setAttribute("listRoomArea",listRoomArea);
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String city = (request.getParameter("city") == "" || request.getParameter("city") == null) ? "all" : request.getParameter("city");

        String district = (request.getParameter("district") == "" || request.getParameter("district") == null) ? "all" : request.getParameter("district");
        String ward = (request.getParameter("ward") == "" || request.getParameter("ward") == null) ? "all" : request.getParameter("ward");
        String inputText = request.getParameter("key")!=null? request.getParameter("key").trim():"";
        int page =request.getParameter("page")!=null? Integer.parseInt(request.getParameter("page")):1;
//int page = 1;

        session.setAttribute("CURRENT_PAGE","home");

        request.setAttribute("citySelected", city);

        request.setAttribute("districtSelected", district);

        request.setAttribute("wardSelected", ward);

        request.setAttribute("key",inputText);
        request.setAttribute("page", page);
        System.out.println(city +" city : " + district + " district : " + ward+" ward");
        int total = rd.getTotalRoomsByCondition(city,district,ward,inputText);
        System.out.println("-> get total in condition: " + total);
        List<Room> rooms = rd.getListRoomsByCondition(city,district,ward, inputText,page,12,0,0,0,0,"");

        boolean isSuccess = false;
        if (!rooms.isEmpty()){
            isSuccess=true;
            request.setAttribute("total", Math.ceil((double) total / 12));
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
        Account accRent = (Account) session.getAttribute("USER");

//        System.out.println("--> check acc user: "+ accRent);
        if (accRent != null){
            Date currentDate = new Date();

            // Định dạng ngày thành chuỗi
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                // Chuyển chuỗi createDate thành đối tượng Date
                Date createDateObj = formatter.parse(accRent.getCreateDate());
                Date expiration;
                if (accRent.getExpiredDate() == null){
                    expiration = new Date();
                }else
                 expiration = formatter.parse(accRent.getExpiredDate());

//                System.out.println("create date: "+createDateObj);
                // So sánh ngày hiện tại với ngày từ createDate
                //check tai khoan tao hon 1 ngay chua
                if (currentDate.after(createDateObj)) {
                    //check lan cuoi dang nhap da hon 1 ngay chua

                    if (currentDate.after(expiration)){
//                        rd.updateExpiredDateRoomId(accRent.getAccId());
//                        System.out.println("suggest");
                        Room mostView = rd.getRoomMostView();
                        Room recently = rd.getRoomById(accRent.getRecentlyRoom());
//                        System.out.println("recently: "+recently);
                        if (recently != null){
                            session.setAttribute("recently",recently);
                            Room budget = rd.getbudgetRoom(recently.getHostelId());
                            session.setAttribute("budget",budget);
//                            System.out.println("budget: "+budget);

                        }
                        session.setAttribute("mostView",mostView);
                        System.out.println("mostView: "+mostView);

                    }
                }
            } catch (ParseException e) {
                System.out.println(e);
            } catch (SQLException e) {
                System.out.println(e);;
            }
        }



        request.getRequestDispatcher("home.jsp").forward(request,response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}