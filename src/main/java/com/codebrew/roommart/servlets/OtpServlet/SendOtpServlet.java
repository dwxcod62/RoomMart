package com.codebrew.roommart.servlets.OtpServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SendOtpServlet", value = "/SendOtpServlet")
public class SendOtpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("a");
    }

    private void sendOTP(HttpServletRequest request, HttpServletResponse response){

    }

}