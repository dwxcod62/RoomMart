package com.codebrew.roommart.servlets.OwnerServlet.Profile;

import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetOwnerProfileServlet", value = "/GetOwnerProfileServlet")
public class GetOwnerProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int type = request.getParameter("type") == null ? 0 : Integer.parseInt(request.getParameter("type"));
        try {
            HttpSession session = request.getSession();
            Account ownerAccount = (Account) session.getAttribute("USER");
            UserInformation information = new UserInformationDAO().getAccountInformationById(ownerAccount.getAccId());
            ownerAccount.setAccountInfo(information);
            session.setAttribute("CURRENT_PAGE", "account");
            session.setAttribute("USER", ownerAccount);
            request.setAttribute("TYPE", type);
        } catch (Exception e) {
            log("Error at EndRentalServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher("pages/owner/profile.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
