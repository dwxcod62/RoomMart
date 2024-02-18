package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "GetSignContractServlet", value = "/GetSignContractServlet")
public class GetSignContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "error";
        String sign_bytea = request.getParameter("asdfgh");
        String data = request.getParameter("data");

        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");
        SystemDao dao = new SystemDao();

        String owner_mail = null;
        String renter_mail = null;

        try{
            if (data != null) {
                String decodeString = EncodeUtils.decodeString(data);

                renter_mail = (decodeString.split("&")[0]).split("=")[1];
                owner_mail = (decodeString.split("&")[1]).split("=")[1];
            }

            if ( acc != null && owner_mail != null  && renter_mail != null){

                if (acc.getRole() == 1){ // Owner

                    // SEND MAIL FOR USER
                    boolean check = new EmailUtils().sendContractConfirmationEmail(renter_mail, data);
                    dao.updateContractSign(acc.getEmail(), renter_mail, 1, sign_bytea);

                    if (check){
                        url = "dashboard";
                        response.sendRedirect(url);
                    } else {
                        url = "error";
                        Status status = Status.builder()
                                .status(false)
                                .content("Some thing wrong!").build();
                        request.setAttribute("RESPONSE_MSG", status);
                    }

                } else if (acc.getRole() == 3 && acc.getEmail().equals(renter_mail)) { // Renter
                    boolean check = dao.updateContractSign(owner_mail, acc.getEmail(), 3, sign_bytea);

                    if (check){
                        url = "dashboard";
                        response.sendRedirect(url);
                    } else {
                        url = "error";
                        Status status = Status.builder()
                                .status(false)
                                .content("Some thing wrong!").build();
                        request.setAttribute("RESPONSE_MSG", status);
                    }
                }

            } else if ( acc == null) {
                url = "login";
                Status status = Status.builder()
                        .status(false)
                        .content("Vui lòng đăng nhập trước khi dùng dịch vụ")
                        .build();
                request.setAttribute("RESPONSE_MSG", status);
            } else {
                Status status = Status.builder()
                        .status(false)
                        .content("Đừng nghịch nữa coi")
                        .build();
                request.setAttribute("RESPONSE_MSG", status);
            }
        } catch ( Exception e){
            e.printStackTrace();
        }
        request.getRequestDispatcher(url).forward(request, response);
    }
}