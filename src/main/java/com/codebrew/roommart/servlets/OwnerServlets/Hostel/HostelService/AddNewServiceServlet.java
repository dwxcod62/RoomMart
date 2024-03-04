package com.codebrew.roommart.servlets.OwnerServlets.Hostel.HostelService;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelServiceDAO;
import com.codebrew.roommart.dto.HostelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
