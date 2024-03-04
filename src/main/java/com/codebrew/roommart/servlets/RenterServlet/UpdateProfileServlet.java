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
    public static final String ERROR = "renter-Profile-Update";
    public static final String SUCCESS = "renter-Profile-Update";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("USER");
        int accId = acc.getAccId();
        try{
//            Get new information
            String profileName = request.getParameter("new-name");
            String profileEmail = request.getParameter("new-email");
            String profileBod = request.getParameter("new-birthday");
            int profileSex = Integer.parseInt(request.getParameter("new-sex"));
            String profilePhone = request.getParameter("new-phone");
            String profileCCCD = request.getParameter("new-cccd");

            Information _info = acc.getAccountInfo().getInformation();
            _info.setFullname(profileName);
            _info.setEmail(profileEmail);
            _info.setBirthday(profileBod);
            _info.setSex(profileSex);
            _info.setPhone(profilePhone);
            _info.setCccd(profileCCCD);

            boolean checkUpdateProfile = new InformationDao().updateProfileByAccId(_info, accId);

            AccountInfo _acc = new AccountInfo(_info);
            acc.setAccountInfo(_acc);

            if (checkUpdateProfile) {
                session.setAttribute("USER", acc);
                url = SUCCESS;
                request.setAttribute("MES", "Cập nhật thành công!");
            } else {
                request.setAttribute("MES", "Cập nhật thất bại!");
                session.setAttribute("Error", "Somethings Wrong!");
            }
        } catch (Exception e) {
            log("Error at UpdateHostel: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}