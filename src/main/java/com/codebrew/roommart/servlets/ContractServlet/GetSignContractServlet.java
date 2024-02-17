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

        String decodeString = null;
        JSONObject jsonObject = null;

        try{
            if (data != null) {
                decodeString = EncodeUtils.decodeString(data);
                jsonObject = new JSONObject(decodeString);
            }

            if ( acc != null && jsonObject != null ){
                if (acc.getRole() == 1){ // Owner
                    jsonObject.put("owner_sign", sign_bytea);

                    String object_string = jsonObject.toString();
                    String encode_object_string = EncodeUtils.encodeString(object_string);

                    // SEND MAIL FOR USER
                    boolean check = new EmailUtils().sendContractConfirmationEmail(jsonObject.getString("renter_mail"), encode_object_string) && dao.updateContractOwnerSide(jsonObject);

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

                } else if (acc.getRole() == 3 && acc.getEmail().equals(jsonObject.getString("renter_email"))) { // Renter
                    boolean check = dao.updateContractRenterSide(acc.getEmail(), sign_bytea);
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