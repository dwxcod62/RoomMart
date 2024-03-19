package com.codebrew.roommart.servlets.OwnerServlets.Hostel;

import com.codebrew.roommart.dao.OwnerDao.IHostelDAO;
import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ShowListHostelServlet", value = "/listHostel")
public class ShowListHostelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account acc;
        IHostelDAO hostelDAO = new HostelDAO();
        HttpSession session = request.getSession();
        acc = (Account) session.getAttribute("USER");
        int accountId = acc.getAccId();

        List<Hostel> listHostel = hostelDAO.getHostelByOwnerId(accountId); // status = 0, khu trọ đã được phê duyệt

        Map<Integer, Integer> ListNumberTotalRoomsOfHostel = new HashMap<>();
        IRoomDAO roomDAO = new RoomDAO();
        if (listHostel.size() > 0) {
            for (Hostel hostel : listHostel) {
                ListNumberTotalRoomsOfHostel.put(hostel.getHostelID(), roomDAO.getNumberRoomSpecificHostel(hostel.getHostelID())); // lấy tổng số phòng của hostel theo hostelId
            }
            request.setAttribute("LIST_TOTAL_ROOM", ListNumberTotalRoomsOfHostel);
        }

        request.setAttribute("LIST_HOSTEL", listHostel);
        session.setAttribute("CURRENT_PAGE", "hostel");

        request.getRequestDispatcher("hostel-page").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
