package com.codebrew.roommart.servlets.AdminServlets;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import com.codebrew.roommart.dao.AccountDao;

import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.HandlerStatus;



@WebServlet(name = "UpdateAccountStatusServlet", value = "/UpdateAccountStatusServlet")
public class UpdateAccountStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("show-list-account");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "AdminListAcc";

        AccountDao accountDao = new AccountDao();

        try {
            int id = Integer.parseInt(request.getParameter("owner_id"));
            int status = Integer.parseInt(request.getParameter("status"));
            boolean check = false;
            switch (status) {
                case 0:
                    check = accountDao.updateAccountStatus(id, 1);
                    break;
                case 1:
                    check = accountDao.updateAccountStatus(id, -1);
                    break;
                case -1:
                    check = accountDao.updateAccountStatus(id, 1);
                    break;
            }
            if (check) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Cập nhật trạng thái tài khoản thành công.").build());
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Cập nhật trạng thái tài khoản thất bại.").build());
            }
        } catch (Exception e) {
            log("Error at DashboardServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
