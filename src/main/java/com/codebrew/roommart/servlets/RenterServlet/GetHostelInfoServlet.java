package com.codebrew.roommart.servlets.RenterServlets;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;

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
        String url = ERROR;
        Account acc;
        List<Infrastructures> infrastructures;
        List<ServiceInfo> serviceInfo;
        UserInformation accInfo;
        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int renterId = acc.getAccId();

            HostelDAO hostelDAO = new HostelDAO();
            RoomDAO roomDAO = new RoomDAO();

            UserInformationDAO userInfoDAO = new UserInformationDAO();
            request.setAttribute("uri", request.getRequestURI());

            //Get Hostel
            Hostel hostel = (Hostel) session.getAttribute("HOSTEL");
            if (hostel == null) {
                hostel = hostelDAO.getHostelByRenterId(renterId);
                session.setAttribute("HOSTEL", hostel);
                url = SUCCESS;
            }

            //Get Room
            Room room = (Room) session.getAttribute("ROOM_INFOR");
            if (room == null) {
                room = roomDAO.getRoomByRenterId(renterId);
                session.setAttribute("ROOM_INFOR", room);
                url = SUCCESS;
            }

//            Hostel hostel = hostelDAO.getHostelByRenterId(renterId);
//            if (hostel != null) {
//                request.setAttribute("HOSTEL", hostel);
//                url = SUCCESS;
//            }
//
//            //Get Hostel Owner Info
//            UserInformation accountInfo = userInfoDAO.getHostelOwnerInfoByRenterId(1);
//            if (accountInfo != null) {
//                request.setAttribute("ACCOUNT_INFOR", accountInfo);
//                url = SUCCESS;
//            }
//
//            //Get Room Info
//            List<Roommate> roommateInfo = new RoommateInfoDAO().getListRoommatesByRenterID(1);
//            int numberOfMembers = roommateInfo.size();
//            request.setAttribute("NUM_OF_MEMBERS", numberOfMembers);
//
//            Room roomInfo = new RoomDAO().getRoomInfoByRenterId(1);
//            if (roomInfo != null) {
//                request.setAttribute("ROOM_INFOR", roomInfo);
//                url = SUCCESS;
//            }
//
//            //Get Infrastructure
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


