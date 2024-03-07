package com.codebrew.roommart.servlets.OwnerServlets.Report;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ReportDetailDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.OwnerDTO.ReportDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetOwnerReportServlet", value = "/getOwnerReport")
public class GetOwnerReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "pages/owner/report/report-list.jsp";
        int type = request.getParameter("type") == null ? 0 : Integer.parseInt(request.getParameter("type"));
        try {
            HttpSession session = request.getSession();
            Account hostelAccount = (Account)session.getAttribute("USER");
            int hostelOwnerId = hostelAccount.getAccId();

            List<ReportDetail> reportNoReplyDetailList = new ReportDetailDAO().getListReports(hostelOwnerId, 0);
            List<ReportDetail> reportProcessDetailList = new ReportDetailDAO().getListReports(hostelOwnerId, 1);
            List<ReportDetail> reportFinishedDetailList = new ReportDetailDAO().getListReports(hostelOwnerId, 2);

            List<Hostel> hostelList = new HostelDAO().getHostelByOwnerId(hostelOwnerId);

            session.setAttribute("CURRENT_PAGE", "report");
            request.setAttribute("TYPE", type);
            request.setAttribute("HOSTEL_LIST", hostelList);
            request.setAttribute("REPORT_NO_REPLY_DETAIL_LIST", reportNoReplyDetailList);
            request.setAttribute("REPORT_PROCESS_DETAIL_LIST", reportProcessDetailList);
            request.setAttribute("REPORT_FINISHED_DETAIL_LIST", reportFinishedDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
