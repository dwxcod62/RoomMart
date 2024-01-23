package com.codebrew.roommart.servlets.RenterServlet.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EncodeUtils;
import com.codebrew.roommart.utils.RandomUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Account acc = null;
        AccountDao dao = new AccountDao();

        String uname = request.getParameter("username");
        String pwd = EncodeUtils.hashSHA256(request.getParameter("password"));
        String save = request.getParameter("remember");
        String url = "loginPage";

        try {
//            acc = dao.getAccountByUnamePass(uname, pwd);
            if (acc != null && acc.getStatus() == 1){
                url = "success";
                HttpSession session = request.getSession(true);
                if (session != null){
                    session.setAttribute("USER", acc);
                    if ( save != null){
                        String token = RandomUtils.randomToken(30, uname);
                        int twoDay = 60 * 60 * 24 * 2;
                        Cookie cookie = new Cookie("selector", token);
                        cookie.setMaxAge(twoDay);
                        response.addCookie(cookie);
//                        dao.updateTokenByUserName(token, uname);
                    }
                }
                session.setAttribute("CURRENT_PAGES", "dashboard");
            } else if ( acc != null && acc.getStatus() == -1){
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Your account has been locked!").build());
            } else if ( acc != null && acc.getStatus() == 0) {
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Please verify your account in the email!").build());
            } else {
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Incorrect username or password!").build());
            }
        } catch ( Exception e){
            System.out.println("loi " + e);
        } finally {
            if (acc != null && acc.getStatus() == 1) {
                response.sendRedirect(url);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }
}