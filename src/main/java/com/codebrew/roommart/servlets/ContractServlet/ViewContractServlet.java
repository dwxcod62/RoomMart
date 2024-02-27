package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.ContractDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DateUtils;
import com.codebrew.roommart.utils.Decorations;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewContractServlet", value = "/ViewContractServlet")
public class ViewContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                viewContract(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ViewContractServlet.doPost");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                deleteContract(request, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ViewContractServlet.doPost");
    }

    protected void deleteContract(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");
        Room room = (Room) session.getAttribute("room");

        if (new HostelDAO().checkOwnerRoom(acc.getAccId(), room.getRoomId())) {
            new ContractDAO().deleteContract(room.getRoomId());
            System.out.println("- Xoa hop dong cho room co id : "+ room.getRoomId());
            response.sendRedirect("ownerRoomDetail?roomID=" + room.getRoomId() + "&hostelID=" + room.getHostelId());
        } else {
            System.out.println("- Co loi xay ra khi xoa hop dong cho room co id : "+ room.getRoomId());
            response.sendRedirect("ownerRoomDetail?roomID=" + room.getRoomId() + "&hostelID=" + room.getHostelId());
        }
    }

    protected void viewContract(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ContractDAO dao = new ContractDAO();

        JSONObject json_room_information = null;
        Account acc = (Account) session.getAttribute("USER");

        if ( acc.getRole() == 1){
            Room room = (Room) session.getAttribute("room");
            json_room_information = dao.getContractInfoByAccountIdAndRoomId(acc.getAccId(), acc.getRole(), room.getRoomId());
        } else if ( acc.getRole() == 3){
            try{
                int contract_id = Integer.parseInt(req.getParameter("id"));
                json_room_information = dao.getContractInformationByID(contract_id);
            } catch ( Exception e){
                System.out.println(" - Khong bat duoc id cua contract [Renter]");
            }
        }

        if ( json_room_information != null ){
            System.out.println("Lay hop dong cua phong thanh cong!");

            UserInformation owner_info = UserInformation.builder()
                    .fullname(json_room_information.getString("owner_full_name"))
                    .phone(json_room_information.getString("owner_phone"))
                    .cccd(json_room_information.getString("owner_identify_card"))
                    .address(json_room_information.getString("owner_address"))
                    .birthday(json_room_information.getString("owner_birthday"))
                    .build();

            req.setAttribute("OWNER_INFO", owner_info);
            req.setAttribute("OWNER_SIGN", json_room_information.getString("owner_sign"));

            UserInformation renter_info = UserInformation.builder()
                    .fullname(json_room_information.getString("renter_full_name"))
                    .phone(json_room_information.getString("renter_phone"))
                    .cccd(json_room_information.getString("renter_identify_card"))
                    .birthday(json_room_information.getString("renter_birthday"))
                    .build();

            req.setAttribute("RENTER_INFO", renter_info);
            req.setAttribute("RENTER_SIGN", json_room_information.getString("renter_sign"));

            req.setAttribute("USER_CONTRACT", acc.getRole());

            req.setAttribute("contract_status", json_room_information.getInt("contract_status"));
            session.setAttribute("CONTRACT_ID", json_room_information.getInt("contract_id"));

            req.setAttribute("current_day", new DateUtils().formatDate(json_room_information.getString("current_day")));
            req.setAttribute("room_start_date", new DateUtils().formatDate(json_room_information.getString("room_start_date")));
            req.setAttribute("room_end_date", new DateUtils().formatDate(json_room_information.getString("room_end_date")));

            String between = new DateUtils().countYear(json_room_information.getString("room_start_date"), json_room_information.getString("room_end_date"));
            req.setAttribute("between", between);

            req.setAttribute("room_fee", json_room_information.getInt("room_fee"));
            req.setAttribute("payment_term", json_room_information.getInt("payment_term"));
            req.setAttribute("room_deposit", json_room_information.getInt("room_deposit"));

            req.getRequestDispatcher("view-contract-page").forward(req, res);
        } else {
            Status status = Status.builder()
                    .status(false)
                    .content("Something was wrong ! try again").build();
            req.getRequestDispatcher("error").forward(req, res);
        }
    }
}