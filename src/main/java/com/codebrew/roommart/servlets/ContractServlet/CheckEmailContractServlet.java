package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.AccountDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CheckEmailContractServlet", value = "/CheckEmailContractServlet")
public class CheckEmailContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        boolean existsInDatabase = new AccountDao().checkAccountExistByRole(email, 3);

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.valueOf(existsInDatabase));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}