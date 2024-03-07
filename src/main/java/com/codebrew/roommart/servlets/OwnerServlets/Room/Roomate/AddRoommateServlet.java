package com.codebrew.roommart.servlets.OwnerServlets.Room.Roomate;

import com.codebrew.roommart.dao.OwnerDao.Impl.AccountDAO;
import com.codebrew.roommart.dao.RoommateInfoDao;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.dto.RoommateInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddRoommateServlet", value = "/AddRoommateServlet")
public class AddRoommateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        AccountDAO accountDAO = new AccountDAO();
        RoommateInfoDao roommateInfoDAO = new RoommateInfoDao();

        String roomId = request.getParameter("roomID");
        try {
            // Get parameters from client (room details -> Add roommate member button -> Form)
            int roomCapacity = Integer.parseInt(request.getParameter("room-capacity"));
            int accountRenterId = Integer.parseInt(request.getParameter("account-renter-id"));
            String fullName = request.getParameter("full-name");
            String dateOfBirth = request.getParameter("dob");
            int gender = Integer.parseInt(request.getParameter("gender"));
            String phoneNumber = request.getParameter("phone-number");
            String email = request.getParameter("email");
            String cccd = request.getParameter("cccd");
            String address = request.getParameter("address");
            String parentName = request.getParameter("parent-name");
            String parentPhone = request.getParameter("parent-phone");

            // Check get accountId is true or false
            if (accountRenterId > 0) {
                List<RoommateInfo> listCurrentRoommate = roommateInfoDAO.getListRoommatesOfAnAccount(accountRenterId);

                if (listCurrentRoommate.size() < roomCapacity) {
                    Information information = Information.builder()
                            .fullname(fullName)
                            .birthday(dateOfBirth)
                            .sex(gender)
                            .email(email)
                            .phone(phoneNumber)
                            .address(address)
                            .cccd(cccd).build();
                    RoommateInfo roommateInfo = RoommateInfo.builder()
                            .information(information)
                            .parentName(parentName)
                            .parentPhone(parentPhone).build();

                    // Insert into RoommateInformation table
                    boolean check = roommateInfoDAO.AddRoommateInformationOfAnAccount(roommateInfo, accountRenterId);

                    if (check) {
                        request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                                .status(true)
                                .content("Thêm thành viên mới vào phòng thành công!").build());
                    } else {
                        request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                                .status(false)
                                .content("Đã có lỗi xảy ra! Thêm thành viên mới vào phòng thất bại!").build());
                    }
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Phòng đã đạt số lượng thành viên tối đa! Thêm thành viên thất bại!").build());
                }
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
            }
        } catch (Exception e) {
            log("Error at AddRoommateServlet: " + e.toString());
        } finally {
//            request.getRequestDispatcher("roomDetail?roomID=" + roomId + "?hostelID=" + hostellId).forward(request, response);
            response.sendRedirect("ownerRoomDetail");
        }
    }
}
