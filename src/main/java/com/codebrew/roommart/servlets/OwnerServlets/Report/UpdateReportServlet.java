package com.codebrew.roommart.servlets.OwnerServlets.Report;

import com.codebrew.roommart.dao.OwnerDao.Impl.AccountDAO;
import com.codebrew.roommart.dao.ReportDao;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateReportServlet", value = "/update-report")
public class UpdateReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "report-detail?reportId=";
        try {
            String action = request.getParameter("action");
            int reportId = Integer.parseInt(request.getParameter("reportId"));
            Report report = new ReportDao().getReportById(reportId);
            AccountInfo accountInfo = new AccountDAO().getAccountInformationById(report.getSendAccountID());
            if (action.equals("reply")) {
                String responseMsg = request.getParameter("response");
                boolean updateResult = new ReportDao().updateReportToProcess(reportId, responseMsg); // set status = 1 ( đang xử lý)
                if (updateResult) {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Xác nhận và đưa báo cáo vào trạng thái đang xử lý thành công!").build());
                    request.setAttribute("SOCKET_MSG", "Chủ trọ vừa phản hồi báo cáo của bạn");
//                    new MailUtils().sendMailReplyReport(accountInfo.getInformation().getEmail(), String.valueOf(reportId));
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
                }
            } else if (action.equals("finished")) {
                boolean updateResult = new ReportDao().updateReportToFinished(reportId); // set status = 2 đã xử lý
                if (updateResult) {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Xác nhận và hoàn thành báo cáo thành công!").build());
                    request.setAttribute("SOCKET_MSG", "Có một báo cáo của bạn vừa được xử lí xong!");
//                    new MailUtils().sendMailReplyReport(accountInfo.getInformation().getEmail(), String.valueOf(reportId));
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
                }
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
            }
            url += reportId;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
