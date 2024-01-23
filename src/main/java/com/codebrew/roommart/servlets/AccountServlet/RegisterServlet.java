package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.RandomUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "register";
        response.sendRedirect(url);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SystemDao dao = new SystemDao();
        String url = "otp-input";
        boolean status = false;

        String mail = request.getParameter("email");

        try {
            if (dao.checkEmailAvaiable(mail)){
                String token = RandomUtils.randomResToken(16);
                String otp = RandomUtils.randomOTP(4);

                status = EmailUtils.sendToken(mail, otp, token) && dao.resAddEmailTokenOtp(mail, token, otp);
                if (status){
                    url = url + "?token=" + token + "&mail=" + EmailUtils.shortEmail(mail) + "&act=page";
                } else {
                    url = "register";
                    request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Email has already been registerer!").build());
                    System.out.println("Send email fail!");
                }

            } else {
                url = "register";
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Email has already been registerer!").build());
            }
        } catch ( Exception e) {
            System.out.println(e);
        } finally {
            if (status) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }
}