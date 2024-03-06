package com.codebrew.roommart.servlets.OwnerServlets.Room;

import com.codebrew.roommart.dao.OwnerDao.Impl.BillDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetListInvoiceRoomServlet", value = "/GetListInvoiceRoomServlet")
public class GetListInvoiceRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "RoomListBill";
        try {
            HttpSession session = request.getSession();
            int hostelID = ((Hostel) session.getAttribute("hostel")).getHostelID();
            int accID = ((Account) session.getAttribute("USER")).getAccId();
            int roomId = ((Room) session.getAttribute("room")).getRoomId();

            List<Bill> listRoomBill = new BillDAO().getListBillByRoomID(roomId);
            request.setAttribute("listRoomBill", listRoomBill);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
