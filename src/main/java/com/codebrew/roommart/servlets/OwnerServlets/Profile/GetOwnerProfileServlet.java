package com.codebrew.roommart.servlets.OwnerServlets.Profile;


import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.Information;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "GetOwnerProfileServlet", value = "/GetOwnerProfileServlet")
public class GetOwnerProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "profile-page";
        int type = request.getParameter("type") == null ? 0 : Integer.parseInt(request.getParameter("type"));
        try {
            HttpSession session = request.getSession();
            Account ownerAccount = (Account) session.getAttribute("USER");
            Information information = new InformationDao().getAccountInformationById(ownerAccount.getAccId());  // lấy info
            ownerAccount.setAccountInfo(AccountInfo.builder().information(information).build()); // set lại info theo dto
            session.setAttribute("CURRENT_PAGE", "account");
            session.setAttribute("USER", ownerAccount);
            request.setAttribute("TYPE", type);
        } catch (Exception e) {
            log("Error at EndRentalServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
