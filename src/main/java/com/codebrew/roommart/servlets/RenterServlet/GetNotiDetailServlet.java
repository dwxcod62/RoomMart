package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Notification;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetNotiDetailServlet", value = "/GetNotiDetailServlet")
public class GetNotiDetailServlet extends HttpServlet {
    public static final String ERROR = "renter-Notification-Detail";
    public static final String SUCCESS = "renter-Notification-Detail";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            Notification notifications = new NotificationDao().getNotificationById(id);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().write(notifications.getContent());

        } catch ( Exception e){
            System.out.println(e);
        }
    }

}