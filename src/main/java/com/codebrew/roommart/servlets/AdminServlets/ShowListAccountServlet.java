package com.codebrew.roommart.servlets.AdminServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;

@WebServlet(name = "ShowListAccountServlet", value = "/ShowListAccountServlet")
public class ShowListAccountServlet extends HttpServlet {

    private static final String url = "show-list-account";

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountDao accountDao = new AccountDao();

        try {
            HttpSession session = request.getSession();
// Get all account not only Owner
            // Xem loi o dau
           List<Account> list = accountDao.GetAccountsBy2Role(1,2);
//            List<Account> list = accountDao.GetAccountsByRole(1);

            request.setAttribute("OWNER_LIST", list);
            session.setAttribute("CURRENT_PAGE", "account");
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