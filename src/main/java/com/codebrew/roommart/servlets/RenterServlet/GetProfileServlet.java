package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.HostelDAO;
import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Infrastructures;
import com.codebrew.roommart.dto.ServiceInfo;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetProfileServlet", value = "/GetProfileServlet")
public class GetProfileServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-profile.jsp";
    public static final String SUCCESS = "/pages/renter/renter-profile.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        List<Infrastructures> infrastructures = new ArrayList<>();
        List<ServiceInfo> serviceInfo = new ArrayList<>();
        UserInformation accInfo;

        try {
            request.setAttribute("uri", request.getRequestURI());
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int renterId = acc.getAccId();
            HostelDAO hostelDAO = new HostelDAO();

            //Get Account Infor
            accInfo = new UserInformationDAO().getAccountInformationById(renterId);
            if (accInfo != null) {
                request.setAttribute("ACC_INFO", accInfo);
                url = SUCCESS;
            }

            session.setAttribute("CURRENT_PAGE", "hostel-renter-page");
        } catch (Exception e) {
            log("Error at GetHostelInfoServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        doGet(request, respone);
    }
}