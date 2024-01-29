package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.xml.registry.infomodel.User;
import java.io.IOException;

@WebServlet(name = "TestUpdateInfoServlet", value = "/TestUpdateInfoServlet")
public class TestUpdateInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("dashboard");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("full_name");
        String birthday = request.getParameter("birthday");
        String phone = request.getParameter("phone");
        String cccd = request.getParameter("cccd");
        String address = request.getParameter("address");

        SystemDao dao = new SystemDao();

        try{
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");
            UserInformation info = account.getAccountInfo();

            info.setFullname(fullname);
            info.setBirthday(birthday);
            info.setPhone(phone);
            info.setCccd(cccd);
            info.setAddress(address);

            account.setAccountInfo(info);
            session.setAttribute("USER", account);

            if (dao.updateUserInformation(account)){
                request.setAttribute("RESPONSE_MSG", Status.builder().status(true).content("Success!").build());
            } else {
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Some thing wrong, try again!").build());
            }

        } catch ( Exception e){

        } finally {
            request.getRequestDispatcher("dashboard").forward(request, response);
        }
    }
}