package com.codebrew.roommart.servlets.AccountServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.utils.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@WebServlet(name = "RecoverPasswordServlet", value = "/RecoverPasswordServlet")
public class RecoverPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("USER");

            if (account == null) {
                request.getRequestDispatcher("recover-password-page").forward(request, response);
            } else {
                request.getRequestDispatcher("dashboard").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "recover-password-page";
        try {
            HandlerStatus handlerStatus;
            String email = request.getParameter("email");
            AccountDao accountDao = new AccountDao();
            Account account = accountDao.getAccountByEmail(email);
            if (account == null) {
                handlerStatus = HandlerStatus.builder()
                        .status(false)
                        .content("Email bạn vừa nhập không ứng với bất kì tài khoản nào trong hệ thống! Vui lòng kiểm tra lại!").build();
            } else {
                if (account.getRole() == -1) {
                    handlerStatus = HandlerStatus.builder()
                            .status(false)
                            .content("Tài khoản liên kết với email này đã bị khóa nên không thể phục hồi mật khẩu! Vui lòng liên hệ với quản trị viên!").build();
                } else {
                    String userEmail = accountDao.getAccountInformationById(account.getAccId()).getInformation().getEmail();
                    String requestRecoverPasswordCode = RandomUtils.randomOTP(5);


                    String domain = "http://" + ConfigEmailUtils.domain +  "/RoomMart/recover-password-reset?data=";
                    String recoverPasswordUrl = "accountId=" + account.getAccId() + "&recoverCode=" + requestRecoverPasswordCode;
                    String encodeString = EncodeBase64Utils.encodeStringBase64(recoverPasswordUrl);
                    if (new EmailUtils().sendRecoverMail(userEmail, domain+encodeString)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Calendar startTime = Calendar.getInstance();
                        long timeInSecs = startTime.getTimeInMillis();
                        Timestamp endTime = new Timestamp(timeInSecs + (5 * 60 * 1000));

                        boolean insertResult = accountDao.addKeyAndExpirationTimeForPasswordRecoveryRequest(requestRecoverPasswordCode, sdf.format(endTime), account.getAccId());
                        if (insertResult) {
                            handlerStatus = HandlerStatus.builder()
                                    .status(true)
                                    .content("Một email hướng dẫn khôi phục mật khẩu đã được gửi đến email của bạn thành công!").build();
                            url = "recover-password-result-page";
                        } else {
                            handlerStatus = HandlerStatus.builder()
                                    .status(false)
                                    .content("Đã có lỗi xảy ra! Vui lòng kiểm tra lại hoặc thử lại sau!").build();
                        }
                    } else {
                        handlerStatus = HandlerStatus.builder()
                                .status(false)
                                .content("Đã có lỗi xảy ra! Vui lòng kiểm tra lại hoặc thử lại sau!").build();
                    }
                }
            }
            request.setAttribute("RESPONSE_MSG", handlerStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

}