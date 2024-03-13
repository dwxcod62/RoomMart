package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TenantContractServlet", value = "/TenantContractServlet")
public class TenantContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "denied";
        HttpSession session = request.getSession();
        String start_day = request.getParameter("room-startdate");
        String end_day = request.getParameter("room-enddate");
        String room_id = request.getParameter("room_id");
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");

        AccountDao dao = new AccountDao();
        ContractDao contractDao = new ContractDao();
        RoomDao roomDao = new RoomDao();

        try {
            Account acc = (Account) session.getAttribute("USER");
            int r_id = Integer.parseInt(room_id);
            int owner_id = dao.getOwnerIdByRoomId(r_id);
            Room r = roomDao.getRoomById(r_id);

            Contract contract = Contract.builder()
                                .room_id(r_id)
                                .hostelOwnerId(owner_id)
                                .renterId(acc.getAccId())
                                .startDate(start_day)
                                .expiration(end_day)
                                .status(-1)
                                .build();
            boolean check = contractDao.insertContract(contract, r.getPrice());
            int s = 0;
            if (check){
                s = 1;
            }
            url = "/roomDetailH?hostelId=" + id1 + "&rid=" + id2 + "&sts=" + s;

        } catch ( Exception e){
            System.out.println(e);
        } finally{
            response.sendRedirect(url);
        }
    }




    private void make_contract(HttpServletRequest req, HttpServletResponse res){
        HttpSession session = req.getSession();
        String start_date = req.getParameter("room_start_date");
        String end_date = req.getParameter("room_end_date");
        int room_id = Integer.parseInt(req.getParameter("room_id"));

        InformationDao infoDao = new InformationDao();
        HostelDao hostelDao = new HostelDao();
        RoomDao roomDao = new RoomDao();

        try {
            Account acc = (Account) session.getAttribute("USER");

            Information _acc_info = infoDao.getAccountInformationById(acc.getAccId());
            Hostel _hostel = hostelDao.getHostelByRoomId(room_id);
            Information _info_owner = infoDao.getAccountInformationById(_hostel.getHostelOwnerAccountID());
            AccountInfo _owner_info = AccountInfo.builder().information(_info_owner).build();
            Room _room = roomDao.getRoomById(room_id);

            Contract _contract = Contract.builder()
                    .startDate(start_date)
                    .room_id(room_id)
                    .hostelOwnerId(_hostel.getHostelOwnerAccountID())
                    .renterId(acc.getAccId())
                    .status(1)
                    .expiration(end_date)
                    .price(_room.getPrice())
                    .build();

            _contract.getRenter_sign();

            List<Infrastructures> _list_Infrastructures = new InfrastructureDao().getRoomInfrastructures(room_id);
            List<ServiceInfo> _list_Services = new ServiceInfoDAO().getServicesOfHostel(_hostel.getHostelID());

            req.setAttribute("CONTRACT_HOSTEL", _hostel);
            req.setAttribute("CONTRACT_ROOM", _room);
            req.setAttribute("CONTRACT_ROOM_INFRASTRUCTURE_LIST", _list_Infrastructures);
            req.setAttribute("CONTRACT_SERVICES_LIST", _list_Services);
            req.setAttribute("CONTRACT_OWNER", _owner_info);
            req.setAttribute("CONTRACT_RENTER", _acc_info);
            req.setAttribute("CONTRACT", _contract);

        } catch (Exception e){
            System.out.println("Error at TenantContractServlet: " + e );
        }

    }
}