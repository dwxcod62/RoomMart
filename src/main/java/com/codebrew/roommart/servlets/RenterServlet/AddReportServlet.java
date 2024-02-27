package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.HostelDAO;
import com.codebrew.roommart.dao.ReportCategoryDAO;
import com.codebrew.roommart.dao.ReportDAO;
import com.codebrew.roommart.dto.*;

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
        try {
            List<ReportCategory> reportCategories = new ReportCategoryDAO().getReportCategory();
            request.setAttribute("REPORT_CATE", reportCategories);
            request.setAttribute("uri", request.getRequestURI());
        } catch (Exception e) {
            log("Error at GetReportCategoryListServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher("Renter-Report").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String sendDate = dateObj.format(formatter);
        HandlerStatus handlerStatus;
        request.setAttribute("uri", request.getRequestURI());
        try {
            HttpSession session = request.getSession();
            Account acc = (Account) session.getAttribute("USER");
            List<ReportCategory> reportCategories = new ReportCategoryDAO().getReportCategory();
            request.setAttribute("REPORT_CATE", reportCategories);
            int accountId = acc.getAccId();
            int cateID = Integer.parseInt(request.getParameter("cateID"));
            Hostel hostel = new HostelDAO().getHostelByRenterId(accountId);
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

            //Add report
            int reportId = new ReportDAO().addReport(report);
            if (reportId > 0){
                request.setAttribute("SUCCESS", "Bạn đã gửi đi báo cáo thành công");
                request.setAttribute("HOSTEL_OWNER_ID", ownerID);
                request.setAttribute("REPORT_ID", reportId);
                handlerStatus = HandlerStatus.builder().status(true).content("Bạn đã gửi đi báo cáo thành công!").build();
            } else {
                handlerStatus = HandlerStatus.builder().status(false).content("Đã có lỗi xảy ra! Gửi báo cáo thất bại!").build();
            }

            request.setAttribute("RESPONSE_MSG", handlerStatus);
        } catch (Exception e) {
            request.setAttribute("RESPONSE_MSG", HandlerStatus.builder().status(false).content("Đã có lỗi xảy ra! Gửi báo cáo thất bại!").build());
            log("Error at AddReportServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher("Renter-report-page").forward(request, response);
        }
    }
}