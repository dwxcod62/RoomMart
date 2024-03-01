package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dao.RoommateInfoDao;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetContractServlet", value = "/GetContractServlet")
public class GetContractServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-contract.jsp";
    public static final String SUCCESS = "/pages/renter/renter-contract.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                Load_Renter_Contract(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "GetContractServlet");
    }

    protected void Load_Renter_Contract(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        try {
            HttpSession session = request.getSession();
            acc = (Account)session.getAttribute("USER");
            int accId = acc.getAccId();
            ContractDao contractDAO = new ContractDao();

            //Get Renter
            Information renterInfo = contractDAO.getContractByRenterId(accId);
            if (renterInfo != null) {
                request.setAttribute("RENTER_INFO", renterInfo);
                url = SUCCESS;
            }
            request.setAttribute("uri", request.getRequestURI());

            //Get HostelOwner
            Information ownerInfo = contractDAO.getOwnerByContract(accId);
            if (ownerInfo != null){
                request.setAttribute("OWNER_INFO", ownerInfo);
                url = SUCCESS;
            }

            //Get Hostel Address
            Hostel hostelInfo = contractDAO.getHostelByContract(accId);
            if (hostelInfo != null){
                request.setAttribute("HOSTEL", hostelInfo);
                url = SUCCESS;
            }

            //Get Contract Information
            Contract contractInfo = contractDAO.getInfoContract(accId);
            if (contractInfo != null){
                request.setAttribute("CONTRACT", contractInfo);
                url = SUCCESS;
            }

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