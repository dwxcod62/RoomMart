package com.codebrew.roommart.servlets.AdminServlets;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.ProposeDao;
import com.codebrew.roommart.dao.ServicesDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Propose;
import com.codebrew.roommart.dto.Services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "AdminDashboard", value = "/AdminDashboard")
public class AdminDashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "AdminDashBoardPage";
        AccountDao accountDao = new AccountDao();
        HttpSession session = request.getSession();
        try {
            //Get list account of owner and renter with role id is 1 / 2
            List<Account> totalAccountOwner = accountDao.GetAccountsByRole(1);
            request.setAttribute("totalAccountOwner", totalAccountOwner.size() == 0 ? 0 : totalAccountOwner.size());

            List<Account> totalAccountRenter = accountDao.GetAccountsByRole(2);
            request.setAttribute("totalAccountRenter", totalAccountRenter.size() == 0 ? 0 : totalAccountRenter.size());

            //Get current month and year
            LocalDate currentdate = LocalDate.now();
            int currentMonth = currentdate.getMonthValue();
            String currentYear = String.valueOf(currentdate.getYear());
            String DateNow = currentMonth + "/" + currentYear;
            request.setAttribute("DateNow", DateNow);

            //Get list account owner in recent month of owner and renter
            int totalNewAccountInRecentMonth = accountDao.GetAccountsByRoleInRecentMonth(1);
            request.setAttribute("totalNewAccountInRecentMonth", totalNewAccountInRecentMonth == 0 ? 0 : totalNewAccountInRecentMonth);

            int totalNewAccountInRecentMonthRenter = accountDao.GetAccountsByRoleInRecentMonth(2);
            request.setAttribute("totalNewAccountInRecentMonthRenter", totalNewAccountInRecentMonthRenter == 0 ? 0 : totalNewAccountInRecentMonthRenter);

            //Get list propose is waitting and accept
            List<Propose> proposeList = new ProposeDao().getAllPropose();
            double proposeListWaiting = 0, proposeListAccepted = 0, percenProposeListAccepted = 0;
            if (proposeList.size() != 0) {
                for (Propose p : proposeList) {
                    if (p.getStatus() == 0) {
                        proposeListWaiting++;
                    }
                }
                for (Propose p : proposeList) {
                    if (p.getStatus() == 1) {
                        proposeListAccepted++;
                    }
                }
                percenProposeListAccepted = (proposeListAccepted / proposeList.size()) * 100;
                String percenTemp = String.valueOf(percenProposeListAccepted).substring(0, 3);
                percenProposeListAccepted = Double.parseDouble(percenTemp);
            }
            request.setAttribute("proposeListWaiting", (int) proposeListWaiting);
            request.setAttribute("percenProposeListAccepted", percenProposeListAccepted);

            session.setAttribute("CURRENT_PAGE", "dashboard");
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