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

@WebServlet(name = "GetHostelInfoServlet", value = "/GetHostelInfoServlet")
public class GetHostelInfoServlet extends HttpServlet {
    public static final String ERROR = "renter-Home";
    public static final String SUCCESS = "renter-Home";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                Load_Renter_Hostel_Info(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "GetHostelInfoServlet");
    }

    protected void Load_Renter_Hostel_Info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        List<ServiceInfo> serviceInfo;
        List<Infrastructures> infrastructures;
        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int renterId = acc.getAccId();

            ContractDao contractDAO = new ContractDao();

            session.setAttribute("uri", request.getRequestURI());

            //Get Hostel
            Hostel hostel = (Hostel) session.getAttribute("HOSTEL");
            if (hostel == null) {
                hostel = contractDAO.getHostelByContract(renterId);
                session.setAttribute("HOSTEL", hostel);
                url = SUCCESS;
            }

            //Get Room Info
            Room room = (Room) session.getAttribute("ROOM_INFOR");
            if (room == null) {
                room = contractDAO.getRoomByContract(renterId);
                session.setAttribute("ROOM_INFOR", room);
                url = SUCCESS;
            }

            //Get Hostel Owner Info
            Information userInformation = (Information) session.getAttribute("ACCOUNT_INFOR");
            if (userInformation == null) {
                userInformation = contractDAO.getOwnerByContract(renterId);
                session.setAttribute("ACCOUNT_INFOR", userInformation);
                url = SUCCESS;
            }

            //Count Member
            int numberOfMembers = contractDAO.countMemberByContract(renterId);
            session.setAttribute("NUM_OF_MEMBERS", numberOfMembers);

            //Get Infrastructure
            infrastructures = (List<Infrastructures>) session.getAttribute("INFRASTRUCTURES");
            if (infrastructures == null) {
                infrastructures = contractDAO.getInfrastructuresByContract(renterId);
                session.setAttribute("INFRASTRUCTURES", infrastructures);
                url = SUCCESS;
            }

            //Get Service
            serviceInfo = (List<ServiceInfo>) session.getAttribute("SERVICES");
            if (serviceInfo == null) {
                serviceInfo = contractDAO.getServicesByContract(renterId);
                session.setAttribute("SERVICES", serviceInfo);
                url = SUCCESS;
            }

//            session.setAttribute("CURRENT_PAGE", "hostel-renter-page");
        } catch (Exception e) {
            log("Error at GetHostelInfoServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}