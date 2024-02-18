package com.codebrew.roommart.servlets.OwnerServlet.Room;

import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetRoomListServlet", value = "/getRoomList")
public class GetRoomListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account acc = null;
        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int accountId = acc.getAccId();
            IRoomDAO roomDAO = new RoomDAO();
            List<Room> roomList = roomDAO.getListRoomsByHostelOwnerId(accountId); // lấy room theo ownerId
            List<String> hostelListName = new ArrayList<>();
            for (Room room: roomList) {
                int hostelID = room.getHostelId();
                hostelListName.add(new HostelDAO().getHostelById(hostelID).getHostelName()); //lấy tên của Khu trọ để đổ dữ liệu lên jsp (lọc theo khu trọ)
            }
            session.setAttribute("ROOM_LIST", roomList);
            session.setAttribute("HOSTEL_LIST_NAME", hostelListName);
            session.setAttribute("CURRENT_PAGE", "room");
            request.setAttribute("HOSTEL_LIST", new HostelDAO().getHostelByOwnerId(accountId)); // lấy list hostel theo ownerId
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("pages/owner/room/room-list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
