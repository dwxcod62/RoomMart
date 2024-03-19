package com.codebrew.roommart.servlets.ProposeServlets.Owner;

import com.codebrew.roommart.dao.ProposeDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SendProposeServlet", value = "/SendProposeServlet")
public class SendProposeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            HandlerStatus handlerStatus;
            HttpSession session = request.getSession();
            Account ownerAccount = (Account) session.getAttribute("USER");
            String proposeContent = request.getParameter("propose-content").trim();
            if (!proposeContent.isEmpty()) {
                boolean insertStatus = new ProposeDao().insertNewProposeOwner(proposeContent, ownerAccount.getAccId()); // gửi propose đi, status = 0, đang chờ phản hồi
                if (insertStatus) {
                    handlerStatus = HandlerStatus.builder()
                            .status(true)
                            .content("Gừi đề xuất/ý kiến tới quản trị viên thành công! Cảm ơn vì những đóng góp, phản hồi từ bạn!").build();
                } else {
                    handlerStatus = HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Gửi đề xuất thất bại! Vui lòng thử lại trong chốc lát!").build();
                }
            } else {
                handlerStatus = HandlerStatus.builder()
                        .status(false)
                        .content("Không thể gửi đề xuất nếu bạn không nhập gì cả!").build();
            }
            request.setAttribute("RESPONSE_MSG", handlerStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect("GetProposeServlet");
        }
    }
}
