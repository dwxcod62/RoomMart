package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
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

        JSONObject jsonObject = null;
        UserInformation renter_info = null;
        UserInformation owner_info = null;

        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");

        if ( acc != null){
            try{
                SystemDao dao = new SystemDao();

                if ( acc.getRole()  == 1){
                    renter_info = (UserInformation) session.getAttribute("CONTRACT_RENTER_USER");
                    jsonObject = (JSONObject) session.getAttribute("CONTRACT_INFORMATION");

                    String email = (String) session.getAttribute("RENTER_MAIL");

                    dao.updateContractOwnerSide(jsonObject, acc.getAccId(), acc.getAccountInfo(), renter_info);

                    int contract_id = dao.updateContractSign(acc.getAccId(), renter_info.getAccount_id(), sign_bytea);


                    boolean check = new EmailUtils().sendContractConfirmationEmail(email , contract_id);
                    if (check){
                        url = "dashboard";
                        response.sendRedirect(url);
                    } else {
                        Status status = Status.builder()
                                .status(false)
                                .content("Some thing wrong!").build();
                        request.setAttribute("RESPONSE_MSG", status);
                        request.getRequestDispatcher(url).forward(request, response);
                    }
                } else if ( acc.getRole() == 3){
                    int contract_id = dao.updateRenterContractSign( (int) session.getAttribute("CONTRACT_ID") , sign_bytea);

                    if (contract_id > 0){

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

            } catch ( Exception e){
                e.printStackTrace();
            }
        }
    }
}