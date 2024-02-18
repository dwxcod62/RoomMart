package com.codebrew.roommart.servlets.OwnerServlet.Hostel;

import com.codebrew.roommart.dao.OwnerDao.IHostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.HandlerStatus;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Services;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddNewHostelServlet", value = "/add-new-hostel")
public class AddNewHostelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("pages/owner/hostel/add-new-hostel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Account acc;
        try {
            req.setCharacterEncoding("UTF-8");
            HttpSession session = req.getSession();
            acc = (Account) session.getAttribute("USER");

            int accountId = acc.getAccId();
            String hostelName = req.getParameter("hostel-name");
            String hostelAddress = req.getParameter("hostel-address");
            String hostelProvince = req.getParameter("hostel-province");
            String hostelDistrict = req.getParameter("hostel-district");
            String hostelWard = req.getParameter("hostel-ward");

            new HostelDAO().addNewHostel(new Hostel(accountId, hostelName, hostelAddress, hostelWard, hostelDistrict, hostelProvince));
        } catch (Exception e) {
            log("Error at AddHostel: " + e.toString());
        } finally {
            response.sendRedirect("/RoomMart/owner-hostel-list");
        }
    }
}
