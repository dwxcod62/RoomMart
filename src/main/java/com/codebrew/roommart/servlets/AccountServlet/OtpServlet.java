package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OtpServlet", value = "/OtpServlet")
public class OtpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            checkOTPFromMail(request, response);
            return null;
        }, "OtpServlet.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            checkOTPFromInput(request, response);
            return null;
        }, "OtpServlet.doPost");
    }

    protected void checkOTPFromInput(HttpServletRequest request, HttpServletResponse response){

    }

    protected void checkOTPFromMail(HttpServletRequest request, HttpServletResponse response){
        String url = "login";
        try{
            String data = request.getParameter("data");
            if (data != null){
                String decodeString = EncodeUtils.decodeString(data);
                String[] decodeStringArr = decodeString.split("&");
                if (decodeStringArr.length > 0) {
                    String emailData = decodeStringArr[0];
                    String otpData = decodeStringArr[1];

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

                    String otp;
                    try {
                        otp = otpData.split("=")[1];
                    } catch ( Exception e){
                        Status status = Status.builder()
                                .status(false)
                                .content("Something wrong, try again!")
                                .build();
                        request.setAttribute("RESPONSE_MSG", status);
                        return;
                    }

                    SystemDao dao = new SystemDao();
                    boolean check = dao.checkOTP(email, otp);
                    if (check){
                        System.out.println("check OTP cho " + email + " thanh cong");

                        url = "register-information-form";
                        request.setAttribute("EMAIL", email);
                    } else {
                        System.out.println("check OTP cho " + email + " that bai");

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
            } else {
                Status status = Status.builder()
                        .status(false)
                        .content("Something wrong, try again!")
                        .build();
                request.setAttribute("RESPONSE_MSG", status);
            }
            request.getRequestDispatcher(url).forward(request, response);

        } catch ( Exception e){
            System.out.println(e);
        }
    }
}