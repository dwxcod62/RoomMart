package com.codebrew.roommart.servlets.OwnerServlet.Hostel;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dao.OwnerDao.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.RoomDAO;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Room;

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

        HttpSession session = request.getSession();
        acc = (Account) session.getAttribute("USER");
        int accountId = acc.getAccountId();

        int hostelId = Integer.parseInt(request.getParameter("hostelID"));

        Hostel hostel = new HostelDAO().getHostelByIdWithConstraint(hostelId, accountId); // chưa xử lý DAO

        RoomDAO roomDao = new RoomDAO();

        if (hostel == null) {
            url = ERROR;
            response.sendRedirect(url);
        }

        List<Room> rooms = roomDao.getListRoomsByHostelId(hostelId); // chua xu ly dao
        int numberRoom = roomDao.getNumberRoomSpecificHostel(hostelId); // same




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
