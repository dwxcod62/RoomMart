package com.codebrew.roommart.servlets;

import com.codebrew.roommart.dao.LandDAO;
import com.codebrew.roommart.dao.RoommateInfoDAO;
import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.Roommate;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UpdateRoommateInfoServlet", value = "/UpdateRoommateInfoServlet")
public class UpdateRoommateInfoServlet extends HttpServlet {
    public static final String ERROR = "/pages/index/roommate-update.jsp";
    public static final String SUCCESS = "/pages/index/roommate-update.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        request.setCharacterEncoding("UTF-8");
        try {
            int roommateID = Integer.parseInt(request.getParameter("roommateID"));
            Roommate roommateInfo = new RoommateInfoDAO().getRoommateByID(roommateID);

            String roommateName = getValueOrDefault(request.getParameter("new-name"), roommateInfo.getInformation().getFullname());
            String roommateEmail = getValueOrDefault(request.getParameter("new-email"), roommateInfo.getInformation().getEmail());
            Date roommateBirthday = parseDateOrDefault(request.getParameter("new-birthday"), roommateInfo.getInformation().getBirthday());
            boolean roommateGender = Boolean.parseBoolean(request.getParameter("new-gender"));
            String roommatePhone = getValueOrDefault(request.getParameter("new-phone"), roommateInfo.getInformation().getPhone());
            String roommateAddress = getValueOrDefault(request.getParameter("new-address"), roommateInfo.getInformation().getAddress());
            String roommateCCCD = getValueOrDefault(request.getParameter("new-cccd"), roommateInfo.getInformation().getCccd());

            UserInformation information = UserInformation.builder()
                    .fullname(roommateName)
                    .email(roommateEmail)
                    .birthday(roommateBirthday)
                    .sex(roommateGender)
                    .phone(roommatePhone)
                    .address(roommateAddress)
                    .cccd(roommateCCCD)
                    .build();

            roommateInfo = roommateInfo.builder()
                    .information(information)
                    .build();

            boolean checkUpdateProfile = new UserInformationDAO().updateRoommateInfoByID(roommateInfo, roommateID);
            if (checkUpdateProfile) {
                request.setAttribute("SUCCESS", "Đã cập nhật thông tin thành công");
                request.setAttribute("roommateID", roommateID);
//                HttpSession session = req.getSession();
//                Account account = (Account)session.getAttribute("USER");

                List<Roommate> list = new RoommateInfoDAO().getListRoommatesOfAnAccount(1);
                request.setAttribute("listroommateinfor", list);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Đã xảy ra lỗi! Vui lòng kiểm tra lại");
            }
        } catch (Exception e) {
            log("Error at UpdateHostel: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private String getValueOrDefault(String value, String defaultValue) {
        return value.isEmpty() ? defaultValue : value;
    }

    private Date parseDateOrDefault(String dateStr, Date defaultValue) {
        if (!dateStr.isEmpty()) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                log("Error parsing date: " + e.toString());
            }
        }
        return defaultValue;
    }
}