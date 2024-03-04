package com.codebrew.roommart.servlets.OwnerServlets.Room;

import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

            session.setAttribute("ROOM_LIST", roomList);
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
