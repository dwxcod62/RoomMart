package com.codebrew.roommart.servlets.RenterServlets;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Infrastructures;
import com.codebrew.roommart.dto.ServiceInfo;
import com.codebrew.roommart.dto.UserInfomation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static sun.tools.jconsole.Messages.ERROR;

@WebServlet(name = "GetRoomInfoServlet", value = "/GetRoomInfoServlet")
public class GetRoomInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        List<Infrastructures> infrastructures;
        List<ServiceInfo> serviceInfo;
        UserInfomation accInfo;

        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int renterId = acc.getAccId();

            HostelDAO hostelDAO = new HostelDAO();
            InformationDAO informationDAO = new InformationDAO();
            request.setAttribute("uri", request.getRequestURI());
            //Get Hostel
            Hostel hostel = hostelDAO.getHostelByRenterId(renterId);
            if (hostel != null) {
                req.setAttribute("HOSTEL", hostel);
                url = SUCCESS;
        } catch (Exception e){

        } finally {

        }
    }

}