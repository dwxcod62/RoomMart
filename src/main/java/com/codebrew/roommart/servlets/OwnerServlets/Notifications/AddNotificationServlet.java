package com.codebrew.roommart.servlets.OwnerServlets.Notifications;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.AccountDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.utils.ConfigEmailUtils;
import com.codebrew.roommart.utils.EmailUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AddNotificationServlet", value = "/AddNotificationServlet")
public class AddNotificationServlet extends HttpServlet {
    private final String SUCCESS = "/pages/owner/notification/notification-detail.jsp";
    private final String FAIL = "/pages/owner/notification/notification-list.jsp";
    private final String ERROR = "error-page";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int ownerId = -1;
        int hostelId = -1;
        String title = null;
        String content = null;
        String url = ERROR;
        HandlerStatus handlerStatus = null;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Account owner = (Account) session.getAttribute("USER");
                if (owner != null) {
                    url = FAIL;
                    ownerId = owner.getAccId();
                    hostelId = Integer.parseInt(request.getParameter("noti-hostel-id"));
                    title = request.getParameter("noti-title");
                    content = request.getParameter("noti-content");

                    if (new HostelDAO().checkOwnerHostel(ownerId)) {
                        int notiId = new NotificationDao().creatNotification(ownerId, hostelId, title, content); // thêm mới noti

                        if (notiId > 0) { // check để hiển thị thông báo quá HanderStatus
                            Notification notification = new NotificationDao().getNotificationById(notiId);
                            request.setAttribute("NOTIFICATION", notification);
//                            request.setAttribute("NOTIFICATION_ID", notiId);
                            request.setAttribute("HOSTEL_ID", hostelId);

                            ArrayList<String> accMailList = new ArrayList<>();
                            String mail = null;
                            ArrayList<Integer> renterList = new HostelDAO().getListRenterIdByHostelId(hostelId);
                            for (int id : renterList) {
                                mail = new AccountDAO().getAccountInformationById(id).getInformation().getEmail();
                                if (mail != null) {
                                    accMailList.add(mail);
                                }
                            }

                            if (accMailList != null && accMailList.size() > 0) {
                                String localhost = "localhost:8080";
                                String domain = "http://"+ ConfigEmailUtils.domain + "/RoomMart/RenterNoti";
                                if (new EmailUtils().SendMailNotice(accMailList, domain)) {
                                    handlerStatus = HandlerStatus.builder().status(true).content("Mail đã được gửi thành công. Vui lòng kiểm tra Email của bạn.").build();
                                } else {
                                    handlerStatus = HandlerStatus.builder().status(false).content("Không thể gửi Mail. Vui lòng kiểm tra lại các thông tin.").build();
                                }
                                request.setAttribute("RESPONSE_MSG", handlerStatus);
                            }

                            url = SUCCESS;

                        } else {
                            handlerStatus = HandlerStatus.builder().status(false).content("Gửi thông báo không thành công").build();
                        }

                    } else {
                        handlerStatus = HandlerStatus.builder().status(false).content("Bạn không thể gửi thông báo cho khu trọ này").build();
                    }
                    request.setAttribute("RESPONSE_MSG", handlerStatus);
                }
            }

        } catch (Exception e) {
            log("Error at AddNotificationServlet: " + e);
        } finally {
            System.out.println(url);
            if (ERROR.equalsIgnoreCase(url)) response.sendRedirect(url);
            else request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
