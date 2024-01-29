package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.ContractDAO;
import com.codebrew.roommart.dao.HostelDAO;
import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetContractServlet", value = "/GetContractServlet")
public class GetContractServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-contract.jsp";
    public static final String SUCCESS = "/pages/renter/renter-contract.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        try {
            HttpSession session = request.getSession();
            acc = (Account)session.getAttribute("USER");
            int accId = acc.getAccId();
            UserInformationDAO infoDao = new UserInformationDAO();

            //Get Renter
            UserInformation renterInfo = infoDao.getAccountInformationById(accId);
            if (renterInfo != null) {
                request.setAttribute("RENTER_INFO", renterInfo);
                url = SUCCESS;
            }
            request.setAttribute("uri", request.getRequestURI());
            //Get HostelOwner
            UserInformation ownerInfo = infoDao.getHostelOwnerInfoByRenterId(accId);
            if (ownerInfo!=null){
                request.setAttribute("OWNER_INFO", ownerInfo);
                url = SUCCESS;
            }
            //Get Hostel Address
            HostelDAO hostelDAO = new HostelDAO();
            Hostel hostel = hostelDAO.getHostelByRenterId(accId);
            if (hostel!=null){
                request.setAttribute("HOSTEL", hostel);
                url = SUCCESS;
            }
            //Get Contract Information
            ContractDAO contractDAO = new ContractDAO();
            Contract contract = contractDAO.getContractByRenterId(accId);
            if (contract!=null){
                request.setAttribute("CONTRACT", contract);
                url = SUCCESS;
            }
//
//            session.setAttribute("CURRENT_PAGE", "hostel-renter-page");
        } catch (Exception e) {
            log("Error at GetContractServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}