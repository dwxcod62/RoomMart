package com.codebrew.roommart.servlets.OwnerServlets.Profile;

import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Information;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OwnerUpdateProfileServlet", value = "/OwnerUpdateProfileServlet")
public class OwnerUpdateProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "profile?type=1";
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");

            String fullName = request.getParameter("fullname");
            System.out.println("Fullname:"+fullName);
            int gender = Integer.parseInt(request.getParameter("gender"));
            String birthday = request.getParameter("birthday") == null ? "NULL" : request.getParameter("birthday");
            String cccd = request.getParameter("cccd");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone") == null ? "NULL" : request.getParameter("phone");
            String address = request.getParameter("address") == null ? "NULL" : request.getParameter("address");
            Information information = Information.builder()
                    .fullname(fullName)
                    .sex(gender)
                    .birthday(birthday)
                    .cccd(cccd)
                    .email(email)
                    .phone(phone)
                    .address(address).build();

            boolean updateResult = new InformationDao().updateOwnerProfileByAccId(information, account.getAccId()); // set lại các thuộc tính theo ownerId
            if (updateResult) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Cập nhật thông tin thành công!").build());
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Cập nhật thông tin thất bại!").build());
            }
        } catch (Exception e) {
            log("Error at UpdateProfileServlet: " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }
}
