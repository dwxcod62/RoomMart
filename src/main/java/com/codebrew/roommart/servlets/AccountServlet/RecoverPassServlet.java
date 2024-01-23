package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.RandomUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RecoverPassServlet", value = "/RecoverPassServlet")
public class RecoverPassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("act");
        String code = request.getParameter("code");
        String url = "login";
        try{
            if(act.equals("rcv") && !code.equals("")){
                url = "forgot-rcv?code=" + code;
            } else{
                url = "login";
            }
        } catch (Exception e){

        } finally {
            response.sendRedirect(url);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        SystemDao dao = new SystemDao();
        String url = "forgot";
        try {
            boolean status = dao.checkEmailAvaiable(email);
            if (!status) {
                String code = RandomUtils.randomString(16);
                if ( dao.updateRecoverCode(code, email) && EmailUtils.sendRecoverPass(email, code)){
                    request.setAttribute("RESPONSE_MSG", Status.builder().status(true).content("Success!").build());
                }
            } else {
                request.setAttribute("RESPONSE_MSG", Status.builder().status(false).content("Email not exist!").build());
            }
        } catch (Exception e){

        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }
}