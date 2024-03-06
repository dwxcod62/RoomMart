package com.codebrew.roommart.servlets.OtpServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CheckOtpServlet", value = "/CheckOtpServlet")
public class CheckOtpServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                check_otp(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "CheckOtpServlet.doPost");
    }

    private void check_otp_get(HttpServletRequest request, HttpServletResponse response) throws Exception{

    }

    private void check_otp(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String FAIL = "otp";
        String ERROR = "error-page";

        String url = ERROR;
        String otp = request.getParameter("otp");

        HandlerStatus handlerStatus = null;
        try {
            String userEmail = request.getParameter("email");
            AccountDao dao = new AccountDao();
            if (otp != null && !otp.isBlank() && userEmail != null) {
                int accID = dao.checkAccountByOTP(userEmail, otp);
                if (accID > 0) {

                    dao.updateAccountStatusByEmail(userEmail, 1);

                    HttpSession session = request.getSession(false);
                    Account acc = new AccountDao().getAccountById(accID);
                    session.setAttribute("USER", acc);
                    url = "dashboard";
                    response.sendRedirect(url);
                } else {
                    url = FAIL;
                    handlerStatus = HandlerStatus.builder().status(false).content("Mã OTP không hợp lệ! Vui lòng kiểm tra lại email hoặc nhấn nút gửi lại để nhận mã mới!").build();
                    request.setAttribute("RESPONSE_MSG", handlerStatus);
                    request.setAttribute("EMAIL_REGISTER", userEmail);
                    request.getRequestDispatcher(url).forward(request, response);
                }
            } else {
                System.out.println("a");
            }

        } catch (Exception e) {
            log("Error at CheckOTPServlet: " + e.toString());
        }
    }
}