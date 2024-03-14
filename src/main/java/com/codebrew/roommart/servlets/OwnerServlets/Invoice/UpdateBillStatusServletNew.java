package com.codebrew.roommart.servlets.OwnerServlets.Invoice;

import com.codebrew.roommart.dao.OwnerDao.Impl.PaymentDAO;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateBillStatusServletNew", value = "/UpdateBillStatusServletNew")
public class UpdateBillStatusServletNew extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "list-hostels";
        try {
            HttpSession session = request.getSession();
            Room room = (Room) session.getAttribute("room");
            int roomId = room.getRoomId();

            int billID = Integer.parseInt(request.getParameter("billID"));

            boolean isUpdated = new PaymentDAO().updateBillStatus(billID, 1);

            if (isUpdated) {
                if (session.getAttribute("CURRENT_PAGE").equals("invoice")) {
                    url = "getInvoiceList";
                } else {
                    url = "roomDetail";
                }
                request.setAttribute("roomID", roomId);
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Cập nhật trạng thái hóa đơn thành công!").build());
            } else {
                url = "list-hostels";
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra. Cập nhật trạng thái hóa đơn thất bại!").build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(url);
//            request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect(url);
        }
    }
}
