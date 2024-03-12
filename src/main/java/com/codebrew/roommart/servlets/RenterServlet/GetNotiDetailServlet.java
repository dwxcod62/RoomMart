package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Notification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetNotiDetailServlet", value = "/GetNotiDetailServlet")
public class GetNotiDetailServlet extends HttpServlet {
    public static final String ERROR = "renter-Notification-Detail";
    public static final String SUCCESS = "renter-Notification-Detail";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            request.setAttribute("uri", "/RoomMart/GetNotiDetailServlet");
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            int accID = account.getAccId();

            int notiID = (request.getAttribute("notification_id") != null) ? (int) request.getAttribute("notification_id") : Integer.parseInt(request.getParameter("notification_id")) ;

            Notification notifications = new NotificationDao().getNotificationById(notiID);

            if (notifications != null){
                request.setAttribute("NOTI-DETAIL", notifications);
                System.out.println(notifications);
            url = SUCCESS;
            }

        }catch (Exception e){
            log("Error at GetNotiDetailServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }

}