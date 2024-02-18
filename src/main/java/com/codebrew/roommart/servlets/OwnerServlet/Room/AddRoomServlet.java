package com.codebrew.roommart.servlets.OwnerServlet.Room;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddRoomServlet", value = "/ownerAddRoom")
public class AddRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        try {
            int hostelId = Integer.parseInt(request.getParameter("hostelID"));
            Hostel hostel = new HostelDAO().getHostelById(hostelId);
            if (hostel != null) {
                url = "pages/owner/room/add-new-room.jsp";
                request.setAttribute("hostel", hostel);
            }
        } catch (Exception e) {
            log("Error at AddRoomPageServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
