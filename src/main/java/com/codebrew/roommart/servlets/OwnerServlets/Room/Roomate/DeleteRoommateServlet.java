package com.codebrew.roommart.servlets.OwnerServlets.Room.Roomate;

import com.codebrew.roommart.dao.RoommateInfoDao;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteRoommateServlet", value = "/DeleteRoommateServlet")
public class DeleteRoommateServlet extends HttpServlet {
    private static final String SUCCESS = "get-roommate-infor";
    private static final String ERROR = "get-roommate-infor";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            int roommateID = Integer.parseInt(request.getParameter("roommateID"));
            RoommateInfoDao roommateInfoDAO = new RoommateInfoDao();
            boolean check = roommateInfoDAO.DeleteRoommateInfo(roommateID);
            if (check) {
                url = SUCCESS;
            }

        } catch (Exception e) {
            log("Error at DeleteRoommateServlet: " + e.toString());
        } finally {
//            request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect("ownerRoomDetail");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "ownerDetailRoom?roomID=";
        try {
            HttpSession session = request.getSession();

            int roomId = ((Room) session.getAttribute("room")).getRoomId();
            int renterAccountId = Integer.parseInt(request.getParameter("renter-account-id"));
            int roommateId = Integer.parseInt(request.getParameter("roommate-id"));

            RoommateInfoDao roommateInfoDAO = new RoommateInfoDao();
            boolean deleteStatus = roommateInfoDAO.DeleteRoommateInfo(renterAccountId, roommateId);
            if (deleteStatus) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Xóa thành viên thành công!").build());
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Xóa thành viên thất bại").build());
            }
            url += roomId;
        } catch (Exception e) {
            log("Error at DeleteRoommateServlet: " + e.toString());
        } finally {
//            request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect("ownerRoomDetail");
        }
    }
}
