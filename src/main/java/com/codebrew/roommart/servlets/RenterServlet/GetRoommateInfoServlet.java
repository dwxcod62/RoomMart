package com.codebrew.roommart.servlets.RenterServlets;

import com.codebrew.roommart.dao.RoommateInfoDAO;
import com.codebrew.roommart.dto.Roommate;

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
        try {
//            HttpSession session = request.getSession();

//            Account account = (Account) session.getAttribute("USER");

            List<Roommate> list = new RoommateInfoDAO().getListRoommatesOfAnAccount(1);
            request.setAttribute("listRoommateInfor", list);
            request.setAttribute("uri", request.getRequestURI());
            request.getRequestDispatcher("/pages/index/renter/renter-roommate.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}