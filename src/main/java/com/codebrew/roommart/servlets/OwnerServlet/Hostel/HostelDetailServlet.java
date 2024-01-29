package com.codebrew.roommart.servlets.OwnerServlet.Hostel;


import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceInfoDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.ServiceInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HostelDetailServlet", value = "/detailHostel")
public class HostelDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "list-hostels";
        String ERROR = "error-page";
        Account acc;
        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
//        int accountId = acc.getAccountId();

            int hostelId = Integer.parseInt(request.getParameter("hostelID"));

            Hostel hostel = new HostelDAO().getHostelByIdWithConstraint(hostelId, 1); // accountId ảo

            RoomDAO roomDao = new RoomDAO();

            if (hostel == null) {
                url = ERROR;
                System.out.println("hostel null");
                return;
            }

            List<Room> rooms = roomDao.getListRoomsByHostelId(hostelId);
//        int numberRoom = roomDao.getNumberRoomSpecificHostel(hostelId); // chua can su dung

            List<ServiceInfo> serviceList = new ServiceInfoDAO().getServicesOfHostel(hostelId);
            url = "pages/owner/hostel/hostel-detail.jsp";
            request.setAttribute("hostel", hostel);
            session.setAttribute("hostel", hostel);
            request.setAttribute("roomList", rooms);
            request.setAttribute("serviceInfo", serviceList);
            session.setAttribute("CURRENT_PAGE", "hostel");
        } catch (Exception e) {
            log("Error at HostelDetailServlet: " + e.toString());
        } finally {
            if (ERROR.equalsIgnoreCase(url))
                response.sendRedirect(url);
            else
                request.getRequestDispatcher(url).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
