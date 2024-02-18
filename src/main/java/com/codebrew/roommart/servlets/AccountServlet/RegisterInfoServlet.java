package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.EncodeUtils;

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
        String email = request.getParameter("email");
        String phone = request.getParameter("phone-number");
        String gender = request.getParameter("gender");
        String identify = request.getParameter("cccd");
        String password = EncodeUtils.hashSHA256(request.getParameter("password"));
        String birthdate = request.getParameter("birthdate");

        UserInformation user_info = new UserInformation().builder()
                                    .fullname(name)
                                    .birthday(birthdate)
                                    .sex((gender.equals("male")) ? true : false )
                                    .phone(phone)
                                    .cccd(identify)
                                    .build();
        try {
            int role = 3;
            Account acc = dao.resAddAccount(user_info, email, password, role);
            if (acc != null){
                url = "dashboard";
                HttpSession session = request.getSession(true);
                session.setAttribute("USER", acc);
                response.sendRedirect(url);
            } else {
                Status status = Status.builder()
                                    .status(false)
                                    .content("Some thing wrong, try again!")
                                    .build();
                request.setAttribute("RESPONSE_MSG", status);
                request.getRequestDispatcher(url).forward(request, response);
            }
        }catch (Exception e){ }

    }
}