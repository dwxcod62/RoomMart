package com.codebrew.roommart.servlets.AdminServlets;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageLicenseServlet", value = "/ManageLicenseServlet")
public class ManageLicenseServlet extends HttpServlet {
    private static final String url = "manage-license-page";

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //  AccountDao accountDao = new AccountDao();
        HostelDAO hostelDao = new HostelDAO();
        try {
            HttpSession session = request.getSession();

            List<Hostel> list = hostelDao.GetHostelsForLicense();
            System.out.println(list.get(4).getImgUrl().get(0));
            request.setAttribute("OWNER_LIST_LICENSE", list);
            session.setAttribute("CURRENT_PAGE", "license");
        } catch (Exception e){
            log("Error at ShowListAccountServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}