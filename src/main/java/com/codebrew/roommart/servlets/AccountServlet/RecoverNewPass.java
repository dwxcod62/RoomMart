package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RecoverNewPass", value = "/RecoverNewPass")
public class RecoverNewPass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pass = request.getParameter("pass");
        String code = request.getParameter("code");
        SystemDao dao = new SystemDao();
        try {
            dao.updateRecoverPassword(code, pass);
        } catch (Exception e){

        }finally {
            response.sendRedirect("login");
        }
    }
}