package com.codebrew.roommart.servlets.OwnerServlets.Profile;

import com.codebrew.roommart.dao.OwnerDao.Impl.AccountDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.utils.EncodeUtils;
import com.google.api.client.util.SecurityUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "profile?type=2";
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            String oldPassword = new EncodeUtils().hashMd5( request.getParameter("old-password"));

            if (new AccountDAO().getAccountByUsernameAndPassword(account.getUsername(), oldPassword) != null) {
                String newPassword = new EncodeUtils().hashMd5( request.getParameter("new-password"));
                boolean resultUpdate = new AccountDAO().updateAccountPass(account.getAccId(), newPassword); // set lại psss theo ownerID
                if (resultUpdate) {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Đổi mật khẩu thành công!").build());
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
                }
            } else {
                request.setAttribute("ERROR", "error");
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Mật khẩu cũ không đúng! Vui lòng kiểm tra lại!").build());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            response.sendRedirect(url);
        }
    }
}
