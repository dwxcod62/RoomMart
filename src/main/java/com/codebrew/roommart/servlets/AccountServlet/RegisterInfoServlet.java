package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RegisterInfoServlet", value = "/RegisterInfoServlet")
public class RegisterInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SystemDao dao = new SystemDao();
        String url = "error";

        String name = request.getParameter("name");
        String token = request.getParameter("token");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String identify = request.getParameter("identify");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");

        UserInformation user_info = new UserInformation().builder()
                                    .fullname(name)
                                    .birthday(birthdate)
                                    .address(address)
                                    .sex((gender.equals("male")) ? true : false )
                                    .phone(phone)
                                    .cccd(identify)
                                    .build();

        try {
            Account acc = dao.resAddAccount(user_info, token, password);
            if (acc != null){
                url = "dashboard";
                HttpSession session = request.getSession(true);
                session.setAttribute("USER", acc);
            }
        }catch (Exception e){

        } finally {
            response.sendRedirect(url);
        }
    }
}