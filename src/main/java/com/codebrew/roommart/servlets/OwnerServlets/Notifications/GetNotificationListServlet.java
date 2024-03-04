package com.codebrew.roommart.servlets.OwnerServlets.Notifications;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetNotificationListServlet", value = "/ownerNotificationList")
public class GetNotificationListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ERROR = "error-page";
        String SUCCESS = "pages/owner/notification/notification-list.jsp";
        String url = ERROR;
        try {
            HttpSession session = request.getSession(false);
            if(session != null){
                Account owner = (Account) session.getAttribute("USER");
                if (owner != null){
                    url = SUCCESS;
                    int ownerId = owner.getAccId();
                    List<Notification> notificationList = new NotificationDao().getNotificationByOwnerId(ownerId);
                    List<Hostel> hostelList = new HostelDAO().getHostelByOwnerId(ownerId);
                    request.setAttribute("NOTIFICATION_LIST", notificationList);
                    session.setAttribute("HOSTEL_LIST", hostelList);
                    url = SUCCESS;
                    session.setAttribute("CURRENT_PAGE", "notification");
                }
            }
        }catch (Exception e){
            log("Error at GetNotificationListServlet: " + e);
        }finally {
            if (ERROR.equalsIgnoreCase(url)) response.sendRedirect(url);
            else request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
