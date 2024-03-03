package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Information;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateProfileServlet", value = "/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-profile-update.jsp";
    public static final String SUCCESS = "/pages/renter/renter-profile-update.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("abc");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String url = ERROR;
//        request.setCharacterEncoding("UTF-8");
//        Account acc = new Account();
//        Information accountInfos = new Information();
//        request.setAttribute("uri", request.getRequestURI());
//        System.out.println("abc");
//        try{
//            HttpSession session = request.getSession();
//            acc = (Account) session.getAttribute("USER");
//
//        } catch (Exception e) {
//            log("Error at UpdateHostel: " + e.toString());
//        } finally {
//            request.getRequestDispatcher(url).forward(request, response);
//        }
    }
}