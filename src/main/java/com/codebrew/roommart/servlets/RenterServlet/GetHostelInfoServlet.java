package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetHostelInfoServlet", value = "/GetHostelInfoServlet")
public class GetHostelInfoServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-room-info.jsp";
    public static final String SUCCESS = "/pages/renter/renter-room-info.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                load_hostel_info(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "GetHostelInfoServlet");
    }

    protected void load_hostel_info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        List<ServiceInfo> serviceInfo;
        List<Infrastructures> infrastructures;
        UserInformation accInfo;
        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int renterId = acc.getAccId();

            ContractDAO contractDAO = new ContractDAO();

            request.setAttribute("uri", request.getRequestURI());

            //Get Hostel
            Hostel hostel = (Hostel) session.getAttribute("HOSTEL");
            if (hostel == null) {
                hostel = contractDAO.getHostelByContract(renterId);
                session.setAttribute("HOSTEL", hostel);
                url = SUCCESS;
            }

            //Get Room
            Room room = (Room) session.getAttribute("ROOM_INFOR");
            if (room == null) {
                room = contractDAO.getRoomByContract(renterId);
                session.setAttribute("ROOM_INFOR", room);
                url = SUCCESS;
            }

            //Get Hostel Owner Info
            UserInformation userInformation = (UserInformation) session.getAttribute("ACCOUNT_INFOR");
            if (userInformation == null) {
                userInformation = contractDAO.getOwnerByContractDetails(renterId);
                session.setAttribute("ACCOUNT_INFOR", userInformation);
                url = SUCCESS;
            }

            //Get Room Info
            List<Roommate> roommateInfo = new RoommateInfoDAO().getListRoommatesByRenterID(renterId);
            int numberOfMembers = roommateInfo.size();
            request.setAttribute("NUM_OF_MEMBERS", numberOfMembers);

              //Get Infrastructure
            infrastructures = (List<Infrastructures>) session.getAttribute("INFRASTRUCTURESS");
            if (infrastructures == null) {
                infrastructures = contractDAO.getInfrastructuresByContract(renterId);
                session.setAttribute("INFRASTRUCTURESS", infrastructures);
                url = SUCCESS;
            }
            System.out.println(infrastructures);

//            infrastructures = new InfrastructureDAO().getRoomInfrastructures(1);
//                if (infrastructures.size() > 0) {
//                request.setAttribute("INFRASTRUCTURES", infrastructures);
//                url = SUCCESS;
//            }
//
//            //Get Service
//            serviceInfo = new ServiceInfoDAO().getServicesOfHostel(1);
//            if (serviceInfo != null) {
//                request.setAttribute("SERVICES", serviceInfo);
//                url = SUCCESS;
//            }
//
//            //Get Account Infor
//            accInfo = new InformationDAO().getAccountInformationById(renterId);
//            if (accInfo != null) {
//                req.setAttribute("ACC_INFO", accInfo);
//                url = SUCCESS;
//            }

//            session.setAttribute("CURRENT_PAGE", "hostel-renter-page");
        } catch (Exception e) {
            log("Error at GetHostelInfoServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}


