package com.codebrew.roommart.servlets.OwnerServlet.Room;

import com.codebrew.roommart.dto.Account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewContractCreate", value = "/ViewContractCreate")
public class ViewContractCreate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("USER");

        String room_id = request.getParameter("roomID");
        String hostel_id = request.getParameter("hostelID");



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}