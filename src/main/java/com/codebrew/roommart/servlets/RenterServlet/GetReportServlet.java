package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.ReportDao;
import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.dto.ReportCategory;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetReportServlet", value = "/GetReportServlet")
public class GetReportServlet extends HttpServlet {
    private static final String SUCCESS = "renter-Report";
    private static final String ERROR = "renter-Report";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                Load_Renter_Report(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "GetReportServlet");
    }

    protected void Load_Renter_Report(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        List<ReportCategory> reportCategories = new ArrayList<>();
        List<Report> reports = new ArrayList<>();
        try {
            ReportDao reportDAO = new ReportDao();
            reports = reportDAO.getReportByRenterId(account.getAccId());
            if(reports.size()>0) {
                req.setAttribute("REPORT_LIST", reports);
                url = SUCCESS;
                if (req.getParameter("id") != null) {
                    req.setAttribute("id", req.getParameter("id"));
                    url = "Report-detail";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}