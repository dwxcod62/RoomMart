package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;
import com.codebrew.roommart.utils.RandomUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            getEmailAndRegister(request, response);
            return null;
        }, "RegisterServlet");
    }

    protected void getEmailAndRegister(HttpServletRequest request, HttpServletResponse response){
        SystemDao dao = new SystemDao();
        String url = "otp-input";
        boolean status = false;
        Status st;
        String mail = request.getParameter("email");

        try {
            if (dao.checkEmailAvaiable(mail)){
                String otp = RandomUtils.randomOTP(4);
                String urlRegister = "email=" + mail + "&otp=" + otp;
                String encodeData = EncodeUtils.encodeString(urlRegister);

                status = new EmailUtils().sendToken(mail, otp, encodeData) && dao.resAddEmailOtp(mail, otp);
                if (status){
                    System.out.println("Dang ki User voi email " + mail + " thanh cong");

                    st = Status.builder()
                            .status(true)
                            .content("The password recovery instruction email has been successfully sent to your email!")
                            .build();
                } else {
                    System.out.println("Dang ki User voi email " + mail + " that bai");

                    st = Status.builder()
                            .status(false)
                            .content("Something Wrong! try again.")
                            .build();
                    url = "login";
                }

            } else {
                System.out.println("Dang ki User voi email " + mail + " bi trung");

                st = Status.builder()
                        .status(false)
                        .content("Email has already been registerer!")
                        .build();
                url = "register";
            }
            request.setAttribute("RESPONSE_MSG", st);
        } catch ( Exception e) {
            System.out.println(e);
        } finally {
            try{
                if (status) {
                    response.sendRedirect(url);
                } else {
                    request.getRequestDispatcher(url).forward(request, response);
                }
            } catch ( Exception e){
                e.printStackTrace();
            }
        }
    }


}