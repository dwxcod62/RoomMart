package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.Information;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateProfileServlet", value = "/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-profile-update.jsp";
    public static final String SUCCESS = "/pages/renter/renter-profile-update.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        request.setCharacterEncoding("UTF-8");
        Account acc = new Account();
        Information accountInfos = new Information();
        request.setAttribute("uri", request.getRequestURI());
        try{
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int accId = acc.getAccId();
            String profileName = request.getParameter("new-name");

            Information _info = acc.getAccountInfo().getInformation();
            _info.setFullname(profileName);
            AccountInfo _acc = new AccountInfo(_info);

            boolean checkUpdateProfile = new InformationDao().updateProfileByAccId(accountInfos, accId);
            if (checkUpdateProfile) {
                acc.setAccountInfo(_acc);
                session.setAttribute("USER", acc);
                url = SUCCESS;
                request.setAttribute("MES", "Cập nhật thành công!");
            } else {
                request.setAttribute("MES", "Cập nhật thất bại!");
                session.setAttribute("Error", "Somethings Wrong!");
            }

            System.out.println("abc");
        } catch (Exception e) {
            log("Error at UpdateHostel: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}