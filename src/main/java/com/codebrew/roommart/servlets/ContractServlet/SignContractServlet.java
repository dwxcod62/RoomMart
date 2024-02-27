package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.EmailUtils;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignContractServlet", value = "/SignContractServlet")
public class SignContractServlet extends HttpServlet {
    private static final String error_url = "error";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");

        switch (acc.getRole()){
            case 1:
                System.out.println("- Run with role Owner");
                Decorations.measureExecutionTime(() -> {
                    try {
                        ownerSign(request, response, session);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }, "SignContractServlet.doPost");
                break;
            case 3:
                System.out.println("- Run with role Renter");
                Decorations.measureExecutionTime(() -> {
                    try {
                        renterSign(request, response, session);
                    } catch (ServletException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }, "SignContractServlet.doPost");
                break;
        }

    }




    private void renterSign(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException, ServletException {
        SystemDao dao = new SystemDao();

        String url = error_url;
        Account acc = (Account) session.getAttribute("USER");
        String sign_bytea = request.getParameter("asdfgh");

        int contract_id = dao.updateRenterContractSign( (int) session.getAttribute("CONTRACT_ID") , sign_bytea, acc.getAccId());

        if (contract_id > -1){
            url = "dashboard";
            response.sendRedirect(url);
        } else {
            Status status = Status.builder()
                    .status(false)
                    .content("Some thing wrong!").build();
            request.setAttribute("RESPONSE_MSG", status);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private void ownerSign(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException {
        SystemDao dao = new SystemDao();
        AccountDao acc_dao = new AccountDao();

        String url = error_url;
        String sign_bytea = request.getParameter("asdfgh");

        Account acc = (Account) session.getAttribute("USER");
        JSONObject json_contract_information = (JSONObject) session.getAttribute("CONTRACT_INFORMATION");
        Room room = (Room) session.getAttribute("ROOM_MAKE_CONTRACT");
        String email_renter = json_contract_information.getString("renter_email");
        UserInformation renter_info = acc_dao.getInfoByMailForContact(email_renter);

        int contract_id = dao.updateContractOwnerSign(json_contract_information, acc.getAccId(), acc.getAccountInfo(), renter_info, sign_bytea, room.getRoomId());
        if ( contract_id > - 1){
            System.out.println("- Cap nhat contract_detail cho contract voi id la " + contract_id);

            new EmailUtils().sendContractConfirmationEmail(email_renter , contract_id);
            url = "ownerRoomDetail?roomID=" + room.getRoomId() + "&hostelID=" + room.getHostelId();
            response.sendRedirect(url);
        } else {
            Status status = Status.builder()
                    .status(false)
                    .content("Some thing wrong!").build();
            request.setAttribute("RESPONSE_MSG", status);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}