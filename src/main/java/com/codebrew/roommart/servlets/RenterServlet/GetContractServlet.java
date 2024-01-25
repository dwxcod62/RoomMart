package com.codebrew.roommart.servlets.RenterServlets;

import com.codebrew.roommart.dao.ContractDAO;
import com.codebrew.roommart.dao.LandDAO;
import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.Land;
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
//        Account acc;
        try {
//            HttpSession session = req.getSession();
//            acc = (Account)session.getAttribute("USER");
//            int accId = acc.getAccId();
            UserInformationDAO infoDao = new UserInformationDAO();

            //Get Renter
            UserInformation renterInfo = infoDao.getAccountInformationById(1);
            if (renterInfo != null) {
                request.setAttribute("RENTER_INFO", renterInfo);
                url = SUCCESS;
            }
            request.setAttribute("uri", request.getRequestURI());
            //Get LandOwner
            UserInformation ownerInfo = infoDao.getLandOwnerInfoByRenterId(1);
            if (ownerInfo!=null){
                request.setAttribute("OWNER_INFO", ownerInfo);
                url = SUCCESS;
            }
            //Get Land Address
            LandDAO landDAO = new LandDAO();
            Land land = landDAO.getLandByRenterId(1);
            if (land!=null){
                request.setAttribute("LAND", land);
                url = SUCCESS;
            }
            //Get Contract Information
            ContractDAO contractDAO = new ContractDAO();
            Contract contract = contractDAO.getContractByRenterId(1);
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