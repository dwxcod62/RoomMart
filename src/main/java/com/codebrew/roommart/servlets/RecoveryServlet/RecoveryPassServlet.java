package com.codebrew.roommart.servlets.RecoveryServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;
import com.codebrew.roommart.utils.RandomUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet(name = "RecoveryPassServlet", value = "/RecoveryPassServlet")
public class RecoveryPassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("USER");
            if (acc == null){
                request.getRequestDispatcher("forgot-password-page").forward(request, response);
            } else {
                request.getRequestDispatcher("dashboard");
            }
        } catch ( Exception e){
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "forgot-password-page";
        String email = request.getParameter("email");
        Status status;
        try {
            SystemDao dao = new SystemDao();
            Account acc = dao.getAccountByEmail(email);

            if ( acc == null){
                status = Status.builder()
                        .status(false)
                        .content("The email you entered does not correspond to any account in the system! Please double-check it!")
                        .build();
            } else {
                if ( acc.getRole() == -1) {
                    status = Status.builder()
                            .status(false)
                            .content("The account linked to this email has been locked, so password recovery is not possible! Please contact the administrator!")
                            .build();
                } else {
                    String code = RandomUtils.randomString(16);
                    String recoverUrl = "email=" + email + "&code=" + code;
                    String encodeString = EncodeUtils.encodeString(recoverUrl);

                    if (EmailUtils.sendRecoverPass(email, encodeString)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar startTime = Calendar.getInstance();
                        long timeInSecs = startTime.getTimeInMillis();
                        Timestamp endTime = new Timestamp(timeInSecs + (5 * 60 * 1000));

                        boolean daoStatus = dao.updateRecoverCode(email, code, endTime);
                        if (daoStatus){
                            status = Status.builder()
                                            .status(true)
                                            .content("The password recovery instruction email has been successfully sent to your email!")
                                            .build();
                            url = "login";
                        } else {
                            status = Status.builder()
                                    .status(false)
                                    .content("An error has occurred! Please check again or try again later!")
                                    .build();
                        }
                    } else {
                        status = Status.builder()
                                .status(false)
                                .content("An error has occurred! Please check again or try again later!")
                                .build();
                    }
                }
            }
            request.setAttribute("RESPONSE_MSG", status);
        } catch ( Exception e){
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}