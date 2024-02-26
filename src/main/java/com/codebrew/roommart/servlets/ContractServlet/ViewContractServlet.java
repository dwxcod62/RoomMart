package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.ContractDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DateUtils;
import com.codebrew.roommart.utils.Decorations;
import org.json.JSONObject;

import javax.json.JsonObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewContractServlet", value = "/ViewContractServlet")
public class ViewContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                view_contract(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ViewContractServlet.doPost");
    }

    protected void view_contract(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ContractDAO dao = new ContractDAO();

        Account acc = (Account) session.getAttribute("USER");
        Room room = (Room) session.getAttribute("room");

        String url = "";
        JSONObject jsonObject = dao.getContractInfoByAccountIdAndRoomId(acc.getAccId(), acc.getRole(), room.getRoomId());

        if ( jsonObject != null){
            System.out.println("Lay hop dong cua phong " + room.getRoomId() + " thanh cong!");

            UserInformation owner_info = UserInformation.builder()
                    .fullname(jsonObject.getString("owner_full_name"))
                    .phone(jsonObject.getString("owner_phone"))
                    .cccd(jsonObject.getString("owner_identify_card"))
                    .address(jsonObject.getString("owner_address"))
                    .birthday(jsonObject.getString("owner_birthday"))
                    .build();

            req.setAttribute("OWNER_INFO", owner_info);
            req.setAttribute("OWNER_SIGN", jsonObject.getString("owner_sign"));

            UserInformation renter_info = UserInformation.builder()
                    .fullname(jsonObject.getString("renter_full_name"))
                    .phone(jsonObject.getString("renter_phone"))
                    .cccd(jsonObject.getString("renter_identify_card"))
                    .birthday(jsonObject.getString("renter_birthday"))
                    .build();

            req.setAttribute("RENTER_INFO", renter_info);
            req.setAttribute("RENTER_SIGN", jsonObject.getString("renter_sign"));

            req.setAttribute("USER_CONTRACT", acc.getRole());

            req.setAttribute("contract_status", jsonObject.getString("contract_status"));
            session.setAttribute("CONTRACT_ID", jsonObject.getString("contract_id"));

            req.setAttribute("current_day", new DateUtils().formatDate(jsonObject.getString("current_day")));
            req.setAttribute("room_start_date", new DateUtils().formatDate(jsonObject.getString("room_start_date")));
            req.setAttribute("room_end_date", new DateUtils().formatDate(jsonObject.getString("room_end_date")));

            String between = new DateUtils().countYear(jsonObject.getString("room_start_date"), jsonObject.getString("room_end_date"));
            req.setAttribute("between", between);

            req.setAttribute("room_fee", jsonObject.getInt("room_fee"));
            req.setAttribute("payment_term", jsonObject.getInt("payment_term"));
            req.setAttribute("room_deposit", jsonObject.getInt("room_deposit"));

            req.getRequestDispatcher("pages/owner/contract/view-contract.jsp").forward(req, res);

        }

    }
}