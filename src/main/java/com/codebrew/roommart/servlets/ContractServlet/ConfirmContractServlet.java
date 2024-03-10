package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dao.OwnerDao.Impl.ConsumeDAO;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "ConfirmContractServlet", value = "/ConfirmContractServlet")
public class ConfirmContractServlet extends HttpServlet {
    private final String SUCCESS = "ownerRoomDetail";
    private final String FAIL = "create-room-account-page";
    private final String ERROR = "error";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                confirm_contract_renter(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ConfirmContractServlet.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                confirm_contract_owner(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ConfirmContractServlet.doPost");
    }

    private void confirm_contract_renter(HttpServletRequest req, HttpServletResponse res) throws Exception{
        HttpSession session = req.getSession();
        String url = "error";
        try {
            String data = req.getParameter("data");
            String decode_data = EncodeUtils.decodeString(data);
            RoomDao roomDao = new RoomDao();
            ContractDao contractDao = new ContractDao();
            InformationDao informationDao = new InformationDao();

            Account acc = (Account) session.getAttribute("USER");
            if ( acc != null ){
                if (Objects.equals(acc.getAccountInfo().getInformation().getEmail(), decode_data)){
                    int room_status = roomDao.getRoomStatusByContractAndEmail(decode_data);
                    if (room_status == -1){
                        Contract _contract = contractDao.getContractByUserId(acc.getAccId());
                        session.setAttribute("CONTRACT", _contract);

                        Information _renter_info = acc.getAccountInfo().getInformation();
                        session.setAttribute("CONTRACT_RENTER", _renter_info);

                        AccountInfo _owner_info = new AccountInfo().builder().information(informationDao.getAccountInformationById(_contract.getHostelOwnerId())).build();
                        session.setAttribute("CONTRACT_OWNER", _owner_info);

                        int _hostel_id = new HostelDao().getHostelByRoomId(_contract.getRoom_id());
                        List<ServiceInfo> _list_Services = new ServiceInfoDAO().getServicesOfHostel(_hostel_id);
                        session.setAttribute("CONTRACT_SERVICES_LIST", _list_Services);

                        List<Infrastructures> _list_Infrastructures = new InfrastructureDao().getRoomInfrastructures(_contract.getRoom_id());
                        session.setAttribute("CONTRACT_ROOM_INFRASTRUCTURE_LIST", _list_Infrastructures);

                        Room _room = new RoomDao().getRoomById(_contract.getRoom_id());
                        session.setAttribute("CONTRACT_ROOM", _room);

                        Hostel _hostel = new HostelDao().getHostelById(_hostel_id);
                        session.setAttribute("CONTRACT_HOSTEL", _hostel);

                        req.getRequestDispatcher("ConfirmContract").forward(req, res);

                    } else {
                        url = "denied";
                        res.sendRedirect(url);
                    }
                } else {
                    System.out.println("b");
                    url = "denied";
                    res.sendRedirect(url);
                }
            } else {
                System.out.println("c");
                url = "denied";
                res.sendRedirect(url);
            }
        } catch ( Exception e){
            System.out.println(e);
        }
    }

    private void confirm_contract_owner(HttpServletRequest req, HttpServletResponse res) throws Exception{
        String url = ERROR;
        String sign = req.getParameter("sign");
        try {
            HttpSession session = req.getSession();
            Account acc = (Account) session.getAttribute("USER");
            Contract contract = (Contract) session.getAttribute("CONTRACT");
            Information _renter_info = (Information) session.getAttribute("CONTRACT_RENTER");
            Room r = (Room) session.getAttribute("CONTRACT_ROOM");


            AccountDao accountDao = new AccountDao();
            ContractDao contractDAO = new ContractDao();
            RoomDao roomDAO = new RoomDao();

            if ( acc.getRole() == 1 ){
                Consume _consume = (Consume) session.getAttribute("CONTRACT_CONSUME");
                new ConsumeDAO().updateConsumeNumber(_consume);

                contract.setOwner_sign(sign);
                contract.setStatus(-1);

                new ConsumeDAO().UpdateFirstConsume(r.getRoomId());

                if ( contractDAO.addContractOwner(contract) && roomDAO.updateRoomStatus(r.getRoomId(), -1)){
                    url = SUCCESS + "?roomID" + r.getRoomId() + "&hostelID=" + r.getHostelId();
                    String email_renter = _renter_info.getEmail();
                    String email_renter_encode = EncodeUtils.encodeString(email_renter);
                    new EmailUtils().sendContractConfirmationEmail(email_renter, email_renter_encode);
                }
            } else {
                if ( contractDAO.addContractRenter(contract.getContract_id(), sign) && roomDAO.updateRoomStatus(contract.getRoom_id(), 0) && accountDao.updateRoomForAccount(acc.getAccId(), contract.getRoom_id()) ){
                    url = "dashboard";
                }
            }
        } catch ( Exception e){
            System.out.println(e);
        }
        res.sendRedirect(url);
    }
}