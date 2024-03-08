package com.codebrew.roommart.servlets.ProposeServlets.Owner;

import com.codebrew.roommart.dao.ProposeDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Propose;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "GetProposeServlet", value = "/GetProposeServlet")
public class GetProposeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account ownerAccount = (Account) session.getAttribute("USER");
            List<Propose> proposeList = new ProposeDao().getAllProposeBySenderId(ownerAccount.getAccId());
            request.setAttribute("proposeList", proposeList);
            session.setAttribute("CURRENT_PAGE", "propose");
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("pages/owner/propose.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
