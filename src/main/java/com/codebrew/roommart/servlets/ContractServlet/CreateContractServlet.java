package com.codebrew.roommart.servlets.ContractServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
import org.json.JSONObject;

@WebServlet(name = "CreateContractServlet", value = "/CreateContractServlet")
public class CreateContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "login";
        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");

        if (acc == null ){
            AccountDao dao = new AccountDao();

            UserInformation owner_info = acc.getAccountInfo();
            String renter_email = request.getParameter("room-email");
            UserInformation renter_info = dao.getInfoByMailForContact(renter_email);


        } else {
            Status status = Status.builder()
                            .status(false)
                            .content("Vui lòng đăng nhập trước khi dùng dịch vụ")
                            .build();
            request.setAttribute("RESPONSE_MSG", status);
        }
    }
}