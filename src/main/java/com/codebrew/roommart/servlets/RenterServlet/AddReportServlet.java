package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.ReportCategoryDao;
import com.codebrew.roommart.dao.ReportDao;
import com.codebrew.roommart.dto.*;
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
        String sendDate = dateObj.format(formatter);
        HandlerStatus handlerStatus;
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
                    .sendDate(sendDate)
                    .content(content)
                    .sendAccountID(accountId)
                    .status(0)
                    .replyAccountID(ownerID)
                    .cateID(cateID)
                    .build();
            new ReportDao().addReport(report);

            HandlerStatus status = HandlerStatus.builder().status(true).content("Báo cáo đã được gửi thành công!").build();
            response.getWriter().write(new Gson().toJson(status));
        } catch (Exception e) {
            HandlerStatus status = HandlerStatus.builder().status(false).content("Đã có lỗi xảy ra! Gửi báo cáo thất bại!").build();
            response.getWriter().write(new Gson().toJson(status));
            log("Error at AddReportServlet: " + e.toString());
        } finally {
            response.sendRedirect("RenterReport?success=true");
        }
    }
}