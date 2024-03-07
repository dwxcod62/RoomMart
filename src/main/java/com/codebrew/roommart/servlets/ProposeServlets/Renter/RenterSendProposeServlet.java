package com.codebrew.roommart.servlets.ProposeServlets.Renter;

import com.codebrew.roommart.dao.ProposeDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Propose;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RenterSendProposeServlet", value = "/RenterSendProposeServlet")
public class RenterSendProposeServlet extends HttpServlet {
    public static final String ERROR = "renter-Response";
    public static final String SUCCESS = "renter-Response";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("uri", request.getRequestURI());
        try {
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("USER");
            String proposeContent = request.getParameter("form-input");

            Propose propose = Propose.builder()
                    .content(proposeContent)
                    .sendAccount(acc)
                    .build();

            ProposeDao proposeDao = new ProposeDao();
            boolean success = proposeDao.insertNewPropose(propose);

            if (success) {
                request.setAttribute("successMessage", "Gửi phản hồi thành công!");
            } else {
                request.setAttribute("errorMessage", "Đã xảy ra lỗi khi gửi phản hồi.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi gửi phản hồi.");
        } finally {
            request.getRequestDispatcher(SUCCESS).forward(request, response);
        }
    }
}