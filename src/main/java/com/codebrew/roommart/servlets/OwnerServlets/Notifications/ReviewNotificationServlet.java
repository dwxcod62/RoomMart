package com.codebrew.roommart.servlets.OwnerServlets.Notifications;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.dto.HandlerStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ReviewNotificationServlet", value = "/ReviewNotificationServlet")
public class ReviewNotificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HandlerStatus handlerStatus = null;
        try {
            HttpSession session = request.getSession(false);
            String action = request.getParameter("action");
            if(session != null){

                int notiId =(request.getAttribute("NOTIFICATION_ID") != null) ?
                        (int) request.getAttribute("NOTIFICATION_ID") : Integer.parseInt(request.getParameter("notification_id"));
                System.out.println(notiId);
                if(notiId > 0) {
                    Notification notification = new NotificationDao().getNotificationById(notiId);
                    request.setAttribute("HOSTEL_ID",  request.getAttribute("HOSTEL_ID"));
                    request.setAttribute("NOTIFICATION", notification);
                    if(!"view".equals(action)){
                        handlerStatus = HandlerStatus.builder().status(true).content("Gửi thông báo thành công").build();
                    }
                }else {
                    handlerStatus = HandlerStatus.builder().status(false).content("Có lỗi xảy ra. Vui lòng thử lại").build();
                }
                request.setAttribute("RESPONSE_MSG", handlerStatus);
            }

        }catch (Exception e){
            log("Error at ReviewNotificationServlet: " + e);
        }finally {
            request.getRequestDispatcher("pages/owner/notification/notification-detail.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
