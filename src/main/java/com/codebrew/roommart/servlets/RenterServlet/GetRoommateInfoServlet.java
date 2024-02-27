package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.RoommateInfoDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Roommate;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetRoommateInfoServlet", value = "/GetRoommateInfoServlet")
public class GetRoommateInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                Load_Renter_RoomMate_Info(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "GetRoommateInfoServlet");
    }

    protected void Load_Renter_RoomMate_Info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();

            Account account = (Account) session.getAttribute("USER");

            List<Roommate> list = new RoommateInfoDAO().getListRoommatesByRenterID(account.getAccId());

            request.setAttribute("listRoommateInfor", list);
            request.setAttribute("uri", request.getRequestURI());
            request.getRequestDispatcher("/pages/renter/renter-roommate.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}