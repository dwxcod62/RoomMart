package com.codebrew.roommart.servlets.RenterServlets;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetLandInfoServlet", value = "/GetLandInfoServlet")
public class GetLandInfoServlet extends HttpServlet {
    public static final String ERROR = "/pages/index/renter/renter-room-info.jsp";
    public static final String SUCCESS = "/pages/index/renter/renter-room-info.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        Account acc;
        List<Infrastructures> infrastructures;
        List<ServiceInfo> serviceInfo;
        UserInformation accInfo;
        try {
//            HttpSession session = request.getSession();
//            acc = (Account) session.getAttribute("USER");
//            int renterId = acc.getAccountId();

            LandDAO landDAO = new LandDAO();
            UserInformationDAO userInfoDAO = new UserInformationDAO();
            request.setAttribute("uri", request.getRequestURI());

            //Get Room
            Land land = landDAO.getLandByRenterId(1);
            if (land != null) {
                request.setAttribute("LAND", land);
                url = SUCCESS;
            }

            //Get Land Owner Info
            UserInformation accountInfo = userInfoDAO.getLandOwnerInfoByRenterId(1);
            if (accountInfo != null) {
                request.setAttribute("ACCOUNT_INFOR", accountInfo);
                url = SUCCESS;
            }

            //Get Room Info
            List<Roommate> roommateInfo = new RoommateInfoDAO().getListRoommatesOfAnAccount(1);
            int numberOfMembers = roommateInfo.size();
            request.setAttribute("NUM_OF_MEMBERS", numberOfMembers);

            Room roomInfo = new RoomDAO().getRoomInfoByRenterId(1);
            if (roomInfo != null) {
                request.setAttribute("ROOM_INFOR", roomInfo);
                url = SUCCESS;
            }

            //Get Infrastructure
            infrastructures = new InfrastructureDAO().getRoomInfrastructures(1);
                if (infrastructures.size() > 0) {
                request.setAttribute("INFRASTRUCTURES", infrastructures);
                url = SUCCESS;
            }

            //Get Service
            serviceInfo = new ServiceInfoDAO().getServicesOfLand(1);
            if (serviceInfo != null) {
                request.setAttribute("SERVICES", serviceInfo);
                url = SUCCESS;
            }
//
//            //Get Account Infor
//            accInfo = new InformationDAO().getAccountInformationById(renterId);
//            if (accInfo != null) {
//                req.setAttribute("ACC_INFO", accInfo);
//                url = SUCCESS;
//            }

//            session.setAttribute("CURRENT_PAGE", "hostel-renter-page");
        } catch (Exception e) {
            log("Error at GetLandInforServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}


