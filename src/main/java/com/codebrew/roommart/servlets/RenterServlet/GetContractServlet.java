package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetContractServlet", value = "/GetContractServlet")
public class GetContractServlet extends HttpServlet {
    public static final String ERROR = "renter-Contract";
    public static final String SUCCESS = "renter-Contract";
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
        HttpSession session = request.getSession();
        acc = (Account)session.getAttribute("USER");
        int accId = acc.getAccId();
        ContractDao contractDAO = new ContractDao();
        List<Infrastructures> infrastructures = new ArrayList<>();
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        try {
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

            //Get Room
            Room room = contractDAO.getRoomByContract(accId);
            if (room != null){
                request.setAttribute("ROOM", room);
                url = SUCCESS;
            }

            //Get Infrastructures
            infrastructures = contractDAO.getInfrastructuresByContract(accId);
            if (infrastructures != null) {
                request.setAttribute("INFRASTRUCTURES", infrastructures);
                url = SUCCESS;
            }

            //Get Service
            serviceInfos = contractDAO.getServicesByContract(accId);
            if (serviceInfos != null) {
                request.setAttribute("SERVICES", serviceInfos);
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