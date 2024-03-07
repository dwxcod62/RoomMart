package com.codebrew.roommart.servlets.ProposeServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.codebrew.roommart.dao.ProposeDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;


@WebServlet(name = "HandleProposeServlet", value = "/HandleProposeServlet")
public class HandleProposeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            HandlerStatus handlerStatus = null;
            HttpSession session = request.getSession();
            Account adminAccount = (Account) session.getAttribute("USER");

            int proposeId = Integer.parseInt(request.getParameter("proposeId"));
            String replyMsg = request.getParameter("proposeReply");
            int changeStatus = Integer.parseInt(request.getParameter("changeStatus"));
            boolean updateResult = new ProposeDao().updatePropose(proposeId, replyMsg, changeStatus, adminAccount.getAccId());
            String msg = "";
            switch (changeStatus) {
                case 1:
                    msg = "Phê duyệt";
                    break;
                case -1:
                    msg = "Từ chối";
                    break;
            }
            if (updateResult) {
                handlerStatus = HandlerStatus.builder()
                        .status(true)
                        .content(msg + " đề xuất/ý kiến thành công!").build();
            } else {
                handlerStatus = HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! " + msg + " đề xuất/ý kiến thất bại!").build();
            }
            request.setAttribute("RESPONSE_MSG", handlerStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("manage-propose").forward(request, response);
        }
    }
}