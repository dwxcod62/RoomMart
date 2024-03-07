package com.codebrew.roommart.servlets.OwnerServlets.Room.InfrastructureServlet;

import com.codebrew.roommart.dao.OwnerDao.Impl.InfrastructureDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateInfrastructureStatusServlet", value = "/updateInfrastructureStatus")
public class UpdateInfrastructureStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "ownerRoomDetail";

        InfrastructureDAO infrastructureDAO = new InfrastructureDAO();

        try {
            String[] statusList = request.getParameterValues("status");
            String[] idListInfrastructureRoom = request.getParameterValues("infrastructureId");
            int roomID = Integer.parseInt(request.getParameter("roomID"));
            int count = 0;
            for (String id : idListInfrastructureRoom) {
                int idInfrastructure = Integer.parseInt(id);
                int status = Integer.parseInt(statusList[count]);
                boolean update = infrastructureDAO.updateInfrastructureStatus(idInfrastructure, status);

                if (!update) {
                    request.setAttribute("roomID", roomID);
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Cập nhật cơ sở vật chất thất bại!").build());
                    break;
                } else {
                    request.setAttribute("roomID", roomID);
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Cập nhật cơ sở vật chất thành công!").build());
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.sendRedirect(url);
        }
    }
}
