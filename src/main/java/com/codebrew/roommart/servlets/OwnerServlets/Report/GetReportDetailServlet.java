package com.codebrew.roommart.servlets.OwnerServlets.Report;

import com.codebrew.roommart.dao.OwnerDao.Impl.ReportDetailDAO;
import com.codebrew.roommart.dto.OwnerDTO.ReportDetail;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GetReportDetailServlet", value = "/report-detail")
public class GetReportDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/pages/owner/report/report-detail.jsp";
        try {
            int reportId = Integer.parseInt(request.getParameter("reportId"));

            ReportDetail reportDetail = new ReportDetailDAO().getReportDetailById(reportId); // lấy thông tin report, join với bảng account và bảng room
            request.setAttribute("reportDetail", reportDetail);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
