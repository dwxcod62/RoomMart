package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
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
    public static final String ERROR = "renter-Notification";
    public static final String SUCCESS = "renter-Notification";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc = new Account();
        List<Notification> notifications = new ArrayList<>();
        try {
            HttpSession session = request.getSession();
            acc = (Account)session.getAttribute("USER");
            int accId = acc.getAccId();
            request.setAttribute("uri", request.getRequestURI());

            notifications = new NotificationDao().getNotificationByRenterId(accId);
            if (notifications.size()>0){
                request.setAttribute("NOTIFY", notifications);
                url = SUCCESS;
            }

        }catch (Exception e){
            log("Error at GetNotificationServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }

}