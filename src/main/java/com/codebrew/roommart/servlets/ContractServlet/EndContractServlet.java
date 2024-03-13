package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.HandlerStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EndContractServlet", value = "/EndContractServlet")
public class EndContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "ownerRoomDetail?roomID=";
        try {
            int roomId = Integer.parseInt(request.getParameter("room-id"));
            int renterAccountId = Integer.parseInt(request.getParameter("renter-account-id"));
            int hostelId = new HostelDao().getHosteIdlByRoomId(roomId);

            AccountDao accountDAO = new AccountDao();
            RoomDao roomDAO = new RoomDao();
            ContractDao contractDAO = new ContractDao();

            boolean updateResult = accountDAO.updateNULLRoomForAccount(renterAccountId);

            boolean updateRoomStatusResult = roomDAO.updateRoomStatus(roomId, 1);

            boolean updateContractStatus = contractDAO.EndContract(roomId, renterAccountId);

            if (updateResult && updateRoomStatusResult && updateContractStatus) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Bạn đã kết thúc hợp đồng thành công!").build());
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Kết thúc hợp đồng thất bại!").build());
            }
            url += roomId + "&HostelID=" + hostelId;
            response.sendRedirect(url);
        } catch (Exception e) {
            log("Error at EndRentalServlet: " + e.toString());
        }
    }
}