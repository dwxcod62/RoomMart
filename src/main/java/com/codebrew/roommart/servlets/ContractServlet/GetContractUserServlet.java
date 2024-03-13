package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Contract;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetContractUserServlet", value = "/GetContractUserServlet")
public class GetContractUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ContractDao contractDao = new ContractDao();
        try{
            Account acc = (Account) session.getAttribute("USER");
            List<Contract> contractList = contractDao.getListContractUser(acc.getAccId());

            session.setAttribute("CURRENT_PAGE", "contract");
            request.setAttribute("LIST_CONTRACT", contractList);
            request.getRequestDispatcher("renter-contract-page").forward(request, response);
        } catch ( Exception e){
            System.out.println(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}