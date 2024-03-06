package com.codebrew.roommart.servlets.OwnerServlets.Hostel.HostelService;

import com.codebrew.roommart.dao.RoomDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckRoomExistServlet", value = "/CheckRoomExistServlet")
public class CheckRoomExistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String rn = request.getParameter("roomNum");
                String hid = request.getParameter("hostelID");
                String udr = request.getParameter("updateRoomNumber");
        System.out.println("roomnumber enter: "+ rn);
        System.out.println("hid:" + hid);
                int roomNumber = 0;
                int hostelID = 0;
                int updateRoomNumber = 0;
                try{
                    roomNumber = Integer.parseInt(rn);
                    hostelID = Integer.parseInt(hid);
                    updateRoomNumber = Integer.parseInt(udr);

                }catch (Exception e){
                    System.out.println("pare int error in CheckRoomExistServlet");
                }
        RoomDao rd = new RoomDao();
        boolean isExist = rd.checkRoomExist(roomNumber,hostelID,updateRoomNumber);
        System.out.println("isExist: "+isExist);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(isExist));


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}