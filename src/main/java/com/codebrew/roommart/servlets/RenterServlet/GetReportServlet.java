package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.ReportCategoryDAO;
import com.codebrew.roommart.dao.ReportDAO;
import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.dto.ReportCategory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetReportServlet", value = "/GetReportServlet")
public class GetReportServlet extends HttpServlet {
    private static final String SUCCESS = "/pages/renter/renter-report-view.jsp";
    private static final String ERROR = "/pages/renter/renter-report-view.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        List<ReportCategory> reportCategories = new ArrayList<>();
        List<Report> reports = new ArrayList<>();
        try {
            ReportCategoryDAO reportCategoryDAO = new ReportCategoryDAO();
            ReportDAO reportDAO = new ReportDAO();
            reportCategories = reportCategoryDAO.getReportCategory();
            request.setAttribute("uri", request.getRequestURI());
//            HttpSession session = req.getSession();
//            Account account = (Account) session.getAttribute("USER");
            if (reportCategories.size()>0){
                request.setAttribute("REPORT_CATE", reportCategories);
                System.out.println(reportCategories);
                url = SUCCESS;
                if (request.getParameter("id")!=null) {
                    request.setAttribute("id", request.getParameter("id"));
                    url = "Report-detail";
                }
            }

            reports = reportDAO.getReportByRenterId(1);
            if(reports.size()>0){
                request.setAttribute("REPORT_LIST", reports);
                url = SUCCESS;
                if (request.getParameter("id")!=null) {
                    request.setAttribute("id", request.getParameter("id"));
                    url = "/pages/index/renter/renter-report-view-detail.jsp";
                }
            }

        }catch (Exception e){
            log("Error at GetReportServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}