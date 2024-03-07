package com.codebrew.roommart.servlets.OwnerServlets.Consume;

import com.codebrew.roommart.dao.OwnerDao.Impl.ConsumeDAO;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UpdateConsumeServlet", value = "/UpdateConsumeServlet")
public class UpdateConsumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "owner-get-room-list";
        try {
            int numberElectric = Integer.parseInt(request.getParameter("number-electric"));
            int numberWater = Integer.parseInt(request.getParameter("number-water"));

            int roomID = Integer.parseInt(request.getParameter("roomID"));
            int hostelID = Integer.parseInt(request.getParameter("hostelID"));

            Consume consume = Consume.builder()
                    .numberWater(numberWater)
                    .numberElectric(numberElectric)
                    .status(0)
                    .roomID(roomID).build();

            List<Consume> consumeThisMonth = new ConsumeDAO().getConsumeThisMonth(roomID);

            int consumeNumberElectric = consumeThisMonth.get(consumeThisMonth.size() - 1).getNumberElectric();
            int consumeNumberWater = consumeThisMonth.get(consumeThisMonth.size() - 1).getNumberWater();

            if (numberElectric < consumeNumberElectric || numberWater < consumeNumberWater) {
                url = "ownerRoomDetail?roomID=" + roomID + "&hostelID=" + hostelID;
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Cập nhật số tiêu thụ thất bại! Số điện/nước cập nhật phải lớn hơn số điện nước đầu tháng!").build());
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                boolean isSuccess = new ConsumeDAO().updateConsumeNumber(consume);
                if (isSuccess) {
                    url = "ownerRoomDetail?roomID=" + roomID + "&hostelID=" + hostelID;
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Cập nhật số tiêu thụ thành công!").build());
                    response.sendRedirect(url);
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Cập nhật số tiêu thụ thất bại!").build());
                    request.getRequestDispatcher(url).forward(request, response);

                }
            }

        } catch ( Exception e ){
            e.printStackTrace();
        }
    }
}