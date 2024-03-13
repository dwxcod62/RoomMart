package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListContractServlet", value = "/ListContractServlet")
public class ListContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                get_list_contract(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ListContractServlet.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void get_list_contract(HttpServletRequest req, HttpServletResponse res) throws Exception{
        HttpSession session = req.getSession();
        ContractDao contractDao = new ContractDao();
        try{
            Account acc = (Account) session.getAttribute("USER");
            List<Contract> contractList = contractDao.getListContractRenterSign(acc.getAccId(), -1);
            List<Contract> contract_ongoing = contractDao.getListContractRenterSign(acc.getAccId(), 1);

            System.out.println(contractList.size());

            Contract c = new Contract();
//            Hostel h = new Hostel();
//            h.getHostelName()

            session.setAttribute("CURRENT_PAGE", "contract");
            req.setAttribute("LIST_CONTRACT", contractList);
            req.setAttribute("LIST_CONTRACT_GOING", contract_ongoing);
            req.getRequestDispatcher("contract-page").forward(req, res);
        } catch ( Exception e){
            System.out.println(e);
        }
    }
}