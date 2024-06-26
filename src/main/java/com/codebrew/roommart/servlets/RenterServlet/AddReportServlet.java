package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.ReportCategoryDao;
import com.codebrew.roommart.dao.ReportDao;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.ConfigEmailUtils;
import com.codebrew.roommart.utils.EmailUtils;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "AddReportServlet", value = "/AddReportServlet")
public class AddReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        request.setAttribute("uri", request.getRequestURI());
        try {
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("USER");
            int accountId = acc.getAccId();
            int cateID = Integer.parseInt(request.getParameter("cateID"));
            Hostel hostel = new HostelDao().getHostelByRenterId(accountId);
            int ownerID = hostel.getHostelOwnerAccountID();
            String content = request.getParameter("form-input");
            Report report = Report.builder()
                    .content(content)
                    .sendAccountID(accountId)
                    .status(0)
                    .replyAccountID(ownerID)
                    .cateID(cateID)
                    .build();
            new ReportDao().addReport(report);

            AccountInfo info_owner = new AccountDao().getAccountInformationById(ownerID);
            String domain = "http://" + ConfigEmailUtils.domain + "/RoomMart/owner-report";
            boolean a = new EmailUtils().SendMailReport(info_owner.getInformation().getEmail(), domain );
            HandlerStatus status = HandlerStatus.builder().status(true).content("Báo cáo đã được gửi thành công!").build();
            response.getWriter().write(new Gson().toJson(status));
        } catch (Exception e) {
            HandlerStatus status = HandlerStatus.builder().status(false).content("Đã có lỗi xảy ra! Gửi báo cáo thất bại!").build();
            response.getWriter().write(new Gson().toJson(status));
            System.out.println(e);
        } finally {
            response.sendRedirect("RenterReport?success=true");
        }
    }
}