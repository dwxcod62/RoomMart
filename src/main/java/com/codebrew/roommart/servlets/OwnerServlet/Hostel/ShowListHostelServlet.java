package com.codebrew.roommart.servlets.OwnerServlet.Hostel;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.IHostelDAO;
import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
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
//        acc = (Account) session.getAttribute("USER");
//        int accountId = acc.getAccountId();
        int accountId = 21;
        List<Hostel> listHostel = hostelDAO.getHostelByOwnerId(accountId);

        Map<Integer, Integer> ListNumberTotalRoomsOfHostel = new HashMap<>();
        IRoomDAO roomDAO = new RoomDAO();
        if (listHostel.size() > 0) {
            for (Hostel hostel : listHostel) {
                ListNumberTotalRoomsOfHostel.put(hostel.getHostelID(), roomDAO.getNumberRoomSpecificHostel(hostel.getHostelID()));
            }
            request.setAttribute("LIST_TOTAL_ROOM", ListNumberTotalRoomsOfHostel);
        }

        request.setAttribute("LIST_HOSTEL", listHostel);
        request.setAttribute("CURRENT_PAGE", "hostel");

        request.getRequestDispatcher("pages/owner/hostel/hostel-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
