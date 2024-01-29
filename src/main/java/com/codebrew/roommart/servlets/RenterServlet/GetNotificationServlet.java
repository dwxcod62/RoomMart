package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.HostelDAO;
import com.codebrew.roommart.dao.NotificationDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Notification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetNotificationServlet", value = "/GetNotificationServlet")
public class GetNotificationServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-notification.jsp";
    public static final String SUCCESS = "/pages/renter/renter-notification.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc = new Account();
        List<Notification> notifications = new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            acc = (Account)session.getAttribute("USER");
            int accId = acc.getAccId();
            HostelDAO hostelDAO = new HostelDAO();
            request.setAttribute("uri", request.getRequestURI());

            notifications = new NotificationDAO().getNotificationByRenterId(accId);
            if (notifications.size()>0){
                request.setAttribute("NOTIFY", notifications);
                url = SUCCESS;
            }

//            request.setAttribute("CURRENT_PAGE", "hostel-renter-page");
        }catch (Exception e){
            log("Error at GetHostelInfoServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }

}