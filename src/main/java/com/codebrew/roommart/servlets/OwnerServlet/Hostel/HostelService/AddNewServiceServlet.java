package com.codebrew.roommart.servlets.OwnerServlet.Hostel.HostelService;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelServiceDAO;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.HandlerStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddNewServiceServlet", value = "/add-new-service")
public class AddNewServiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "detailHostel?hostelID=";
        try {
            int hostelId = Integer.parseInt(request.getParameter("hostel-id"));
            int serviceId = Integer.parseInt(request.getParameter("service-id"));
            int servicePrice = Integer.parseInt(request.getParameter("service-price"));

            HostelServiceDAO hostelServiceDAO = new HostelServiceDAO();

            HostelService hostelService = new HostelService(hostelId, serviceId, servicePrice);
            hostelServiceDAO.addNewHostelService(hostelService);
            System.out.println("insert okela");
            url += hostelId;
        } catch (Exception e) {
            log("Error at UpdateServiceServlet: " + e.toString());
        } finally {
            response.sendRedirect("/RoomMart/" + url);
        }
    }
}
