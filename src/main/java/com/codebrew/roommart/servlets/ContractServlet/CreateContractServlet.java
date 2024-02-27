package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DateUtils;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.StringUtils;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateContractServlet", value = "/CreateContractServlet")
public class CreateContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                goToPageCreateContract(request, response);
            } catch (ServletException | IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "CreateContractServlet.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                get_info_contract(request, response);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "CreateContractServlet.doPost");
    }

    protected void get_info_contract(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String url = "login";
        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");

        if (acc != null ){
            JSONObject jsonObject = new JSONObject();
            AccountDao dao = new AccountDao();

            Room room = (Room) session.getAttribute("room");

            String renter_email = request.getParameter("room-email");
            String start_date = request.getParameter("room-startdate");
            String end_date = request.getParameter("room-enddate");

            UserInformation renter_info = dao.getInfoByMailForContact(renter_email);
            renter_info.setCccd(new StringUtils().maskCccd(renter_info.getCccd()));
            renter_info.setPhone(new StringUtils().maskCccd(renter_info.getPhone()));

            // PROPERTIES
            jsonObject.put("renter_email", renter_email);

            jsonObject.put("room_electric", request.getParameter("room-electric"));
            jsonObject.put("room_water", request.getParameter("room-water"));

            jsonObject.put("room_start_date", start_date);
            jsonObject.put("room_end_date", end_date);

            jsonObject.put("room_fee", request.getParameter("room-fee"));
            jsonObject.put("payment_term", request.getParameter("payment-term"));
            jsonObject.put("room_deposit", request.getParameter("room-deposit"));

            jsonObject.put("fixed_years", request.getParameter("fixed-years"));
            jsonObject.put("percentage_increase", request.getParameter("percentage-increase"));

//            Consume consume = Consume.builder()
//                    .numberElectric(Integer.parseInt(request.getParameter("room-electric")))
//                    .numberWater(Integer.parseInt(request.getParameter("room-water")))
//                    .status(0)
//                    .roomID(room.getRoomId())
//                    .build();

//            boolean check = new ConsumeDao().updateConsumeNumber(consume);

            request.setAttribute("room_start_date", start_date);
            request.setAttribute("room_end_date", end_date);
            request.setAttribute("between", new DateUtils().countYear(start_date, end_date));
            request.setAttribute("room_fee", request.getParameter("room-fee"));
            request.setAttribute("payment_term", request.getParameter("payment-term"));
            request.setAttribute("room_deposit", request.getParameter("room-deposit"));
            request.setAttribute("OWNER_INFO", acc.getAccountInfo());
            request.setAttribute("RENTER_INFO", renter_info);
            request.setAttribute("contract_status", 0);

            session.setAttribute("CONTRACT_INFORMATION", jsonObject);
            session.setAttribute("ROOM_MAKE_CONTRACT", room);


            url = "view-contract-page";

        } else {
            System.out.println("- Da co loi xay ra");

            Status status = Status.builder()
                    .status(false)
                    .content("Vui lòng đăng nhập trước khi dùng dịch vụ")
                    .build();
            request.setAttribute("RESPONSE_MSG", status);
        }
        request.getRequestDispatcher(url).forward(request, response);
    }


    protected void goToPageCreateContract(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "error";
        Room room = (Room) session.getAttribute("room");

        if (room != null){
            System.out.println("- Lay ID thanh cong ");
            url = "create-contract-page";
        } else {
            System.out.println("- ID sai ");
            url = "owner-get-room-list";
            Status status = Status.builder()
                    .status(false)
                    .content("Something wrong, try again!").build();

            request.setAttribute("RESPONSE_MSG", status);
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
}