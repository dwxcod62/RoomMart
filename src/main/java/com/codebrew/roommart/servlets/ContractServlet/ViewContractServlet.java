package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ViewContractServlet", value = "/ViewContractServlet")
public class ViewContractServlet extends HttpServlet {

    private final String ERROR = "error";
    private final String FAIL = "create-contract-page";
    private final String SUCCESS = "ConfirmContract";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ContractDao contractDao = new ContractDao();
        AccountDao accDao = new AccountDao();
        HostelDao hostelDao = new HostelDao();

        int hostel_id = -1;
        String url = FAIL;

        int con_id = Integer.parseInt(request.getParameter("id"));

        Contract _contract = contractDao.getContractByContractId(con_id);
        _contract.setContract_id(con_id);
        Account owner = accDao.getAccountById(_contract.getHostelOwnerId());
        Account renter = accDao.getAccountById(_contract.getRenterId());
        Information _renter_info = renter.getAccountInfo().getInformation();
        RoomDao roomDao = new RoomDao();

        try {
            hostel_id = hostelDao.getHosteIdlByRoomId(_contract.getRoom_id());

            if ( hostel_id == -1){
                System.out.println();
            }
            url = SUCCESS;

            List<Infrastructures> _list_Infrastructures = new InfrastructureDao().getRoomInfrastructures(_contract.getRoom_id());
            List<ServiceInfo> _list_Services = new ServiceInfoDAO().getServicesOfHostel(hostel_id);
            Hostel _hostel = hostelDao.getHostelById(hostel_id);
            Room _room = roomDao.getRoomById(_contract.getRoom_id());

            session.setAttribute("CONTRACT_ROOM", _room);
            session.setAttribute("CONTRACT_HOSTEL", _hostel);
            session.setAttribute("CONTRACT_ROOM_INFRASTRUCTURE_LIST", _list_Infrastructures);
            session.setAttribute("CONTRACT_SERVICES_LIST", _list_Services);
            session.setAttribute("CONTRACT_OWNER", owner.getAccountInfo());
            session.setAttribute("CONTRACT_RENTER", _renter_info);
            session.setAttribute("CONTRACT", _contract);
            System.out.println(_contract);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ERROR.equalsIgnoreCase(url)) response.sendRedirect(url);
            else request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int room_id = Integer.parseInt(request.getParameter("room_id"));
            int renter_id = Integer.parseInt(request.getParameter("renter_id"));
            ContractDao contractDao = new ContractDao();
            contractDao.updateStatusContract(room_id, renter_id, -2);
            String url = "contract-list";
            response.sendRedirect(url);

        } catch ( Exception e){
            System.out.println(e);
        }
    }
}