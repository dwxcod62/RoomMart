package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "ConfirmContractServlet", value = "/ConfirmContractServlet")
public class ConfirmContractServlet extends HttpServlet {
    private final String SUCCESS = "ownerRoomDetail";
    private final String FAIL = "create-room-account-page";
    private final String ERROR = "error";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        try {
            String data = req.getParameter("data");
            String decode_data = EncodeUtils.decodeString(data);

            Account acc = (Account) session.getAttribute("USER");
            if ( acc != null ){
                if (Objects.equals(acc.getAccountInfo().getInformation().getEmail(), decode_data)){




                }
            } else {

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
            Contract contract = (Contract) session.getAttribute("CONTRACT");
            Information _renter_info = (Information) session.getAttribute("CONTRACT_RENTER");
            Room r = (Room) session.getAttribute("CONTRACT_ROOM");

            contract.setOwner_sign(sign);
            contract.setStatus(-1);

            ContractDao contractDAO = new ContractDao();
            RoomDao roomDAO = new RoomDao();

            if ( contractDAO.addContractOwner(contract) && roomDAO.updateRoomStatus(r.getRoomId(), -1)){
                url = SUCCESS + "?roomID" + r.getRoomId() + "&hostelID=" + r.getHostelId();
                String email_renter = _renter_info.getEmail();
                String email_renter_encode = EncodeUtils.encodeString(email_renter);
                new EmailUtils().sendContractConfirmationEmail(email_renter, email_renter_encode);
            }

        } catch ( Exception e){
            System.out.println(e);
        }
        res.sendRedirect(url);
    }
}