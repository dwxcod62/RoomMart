package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EmailUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "OtpServlet", value = "/OtpServlet")
public class OtpServlet extends HttpServlet {
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        SystemDao dao = new SystemDao();
        String url = "error";

        String token = request.getParameter("token");
        String mail = request.getParameter("mail");
        String act = request.getParameter("act");
        String otp;
        if ("mail".equals(act)){
            otp = request.getParameter("otp");
        } else {
            otp = request.getParameter("otp1") + request.getParameter("otp2") + request.getParameter("otp3") + request.getParameter("otp4");
        }

        boolean status = false;
        try {
            status = dao.checkOTP(token, otp);
            if (status){
                request.setAttribute("TOKEN", token);
                url = "register_2";
            } else {
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Invalid OTP").build());
                url = "otp-input" + "?token=" + token + "&mail=" + mail + "&act=page";
            }
        } catch ( Exception e) {
            System.out.println(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        if (act != null) {
            process(request, response);
        } else {
            String url = "register";
            response.sendRedirect(url);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }
}