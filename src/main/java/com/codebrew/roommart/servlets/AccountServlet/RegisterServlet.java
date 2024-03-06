package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.utils.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static  String url = "register-page";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                register(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "RegisterServlet.doPost");
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        req.setCharacterEncoding("UTF-8");

        AccountDao accDao = new AccountDao();
        try {
            String fullname = req.getParameter("fullname");
            String phone = req.getParameter("phone");
            String birthday = req.getParameter("birthday");
            String role = req.getParameter("role");
            String address = req.getParameter("address");
            String username = req.getParameter("username");
            String email = req.getParameter("email").toLowerCase().trim();
            String password = new EncodeUtils().hashMd5(req.getParameter("password"));
            String cccd = req.getParameter("cccd");


            if (!accDao.isExistUsername(username)) {
                if (!new InformationDao().isExistEmail(email)) {
                    Information information = Information.builder()
                            .address(address)
                            .birthday(birthday)
                            .phone(phone)
                            .fullname(fullname)
                            .email(email)
                            .cccd(cccd).build();
                    AccountInfo accountInfo = new AccountInfo(information);
                    Account registerAccount = Account.builder()
                            .accId(0)
                            .username(username)
                            .password(password)
                            .status(0)
                            .role(role.equals("owner") ? 1 : 2)
                            .accountInfo(accountInfo).build();

                    String otp = RandomUtils.randomOTP(5);

                    boolean check = accDao.addAnAccount(registerAccount) && accDao.addOTPToAccount(username, otp);

                    if (check) {
                        url = "otp";
                        String data = "email=" + email + "&otp=" + otp;
                        String encode_data = EncodeUtils.encodeString(data);

                        new EmailUtils().sendToken(email, otp, encode_data);

                        req.setAttribute("EMAIL_REGISTER", email);
                        req.getRequestDispatcher("otp").forward(req, resp);
                    } else {
                        req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                                .status(false)
                                .content("Đã có lỗi xảy ra, vui lòng thử lại sau!").build());
                        req.getRequestDispatcher(url).forward(req, resp);
                    }
                } else {
                    req.setAttribute("ERROR_TYPE", "email");
                    req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Email đã tồn tại trong hệ thống!").build());
                    req.setAttribute("fullname", fullname);
                    req.setAttribute("username", username);
                    req.setAttribute("email", email);
                    req.setAttribute("cccd", cccd);
                    req.getRequestDispatcher(url).forward(req, resp);
                }
            } else {
                req.setAttribute("ERROR_TYPE", "username");
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Tài khoản đã tồn tại trong hệ thống!").build());
                req.setAttribute("fullname", fullname);
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                req.setAttribute("cccd", cccd);
                req.getRequestDispatcher(url).forward(req, resp);
            }

        } catch (Exception e) {
            log("Error at RegisterServlet: " + e.toString());
        }
    }

}