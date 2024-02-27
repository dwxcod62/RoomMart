package com.codebrew.roommart.servlets.OwnerServlet.Notifications;

import com.codebrew.roommart.dao.NotificationDAO;
import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.dto.OwnerDTO.HandlerStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReviewNotificationServlet", value = "/ReviewNotificationServlet")
public class ReviewNotificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HandlerStatus handlerStatus = null;
        try {
            HttpSession session = request.getSession(false);
            String action = request.getParameter("action");
            if(session != null){

                int notiId =(request.getAttribute("NOTIFICATION_ID") != null) ?
                        (int) request.getAttribute("NOTIFICATION_ID") : Integer.parseInt(request.getParameter("notification_id"));
                System.out.println(notiId);
                if(notiId > 0) {
                    Notification notification = new NotificationDAO().getNotificationById(notiId);
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
