package com.codebrew.roommart.servlets.RecoveryServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PasswordResetServlet", value = "/PasswordResetServlet")
public class PasswordResetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "error";

        try {
            String data = request.getParameter("data");
            if (data != null){
                String decodeString = EncodeUtils.decodeString(data);
                String[] decodeStringArr = decodeString.split("&");
                if (decodeStringArr.length > 0) {
                    String emailData = decodeStringArr[0];
                    String codeData = decodeStringArr[1];

                    String email;
                    try {
                        email = emailData.split("=")[1];
                    } catch ( Exception e){
                        Status status = Status.builder()
                                            .status(false)
                                            .content("Something wrong, try again!")
                                            .build();
                        request.setAttribute("RESPONSE_MSG", status);
                        return;
                    }

                    String code;
                    try {
                        code = codeData.split("=")[1];
                    } catch ( Exception e){
                        Status status = Status.builder()
                                .status(false)
                                .content("Something wrong, try again!")
                                .build();
                        request.setAttribute("RESPONSE_MSG", status);
                        return;
                    }

                    SystemDao dao = new SystemDao();
                    boolean check = dao.checkAccountRequestRecoverPassword(email, code);
                    if (check){
                        url = "password-reset-page";
                        request.setAttribute("EMAIL", email);
                        request.setAttribute("RECOVER_CODE", code);
                    } else {
                        url = "recover-password";
                        Status status = Status.builder()
                                        .status(false)
                                        .content("This link has expired! Please resend your request for a new password reset.")
                                        .build();
                        request.setAttribute("RESPONSE_MSG", status);
                    }

                } else {
                    Status status = Status.builder()
                            .status(false)
                            .content("Something wrong, try again!")
                            .build();
                    request.setAttribute("RESPONSE_MSG", status);
                }
            } else {
                Status status = Status.builder()
                        .status(false)
                        .content("Something wrong, try again!")
                        .build();
                request.setAttribute("RESPONSE_MSG", status);
            }

        } catch ( Exception e){
            System.out.println("Error at PasswordResetServlet");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "login";
        Status status;
        try {
            String code = request.getParameter("code");
            String password = request.getParameter("password");
            String re_password = request.getParameter("password_confirmation");

            if (!re_password.equals(password)){
                status = Status.builder()
                        .status(false)
                        .content("Something wrong, try again!")
                        .build();
                request.setAttribute("RESPONSE_MSG", status);
                return;
            }

            SystemDao dao = new SystemDao();
            if ( !password.isEmpty() ){
                if ( dao.resetPassword(code, EncodeUtils.hashSHA256(password)) ){
                    new EmailUtils().sendPasswordChangeSuccessEmail(request.getParameter("email"));
                    status = Status.builder()
                                    .status(true)
                                    .content("Password updated successfully!")
                                    .build();
                    request.setAttribute("RESPONSE_MSG", status);
                } else {
                    status = Status.builder()
                            .status(false)
                            .content("Something wrong, try again!")
                            .build();
                    request.setAttribute("RESPONSE_MSG", status);
                }
            } else {
                status = Status.builder()
                        .status(false)
                        .content("Something wrong, try again!")
                        .build();
                request.setAttribute("RESPONSE_MSG", status);
            }

        } catch ( Exception e){
            System.out.println("Error at PasswordResetServlet");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}