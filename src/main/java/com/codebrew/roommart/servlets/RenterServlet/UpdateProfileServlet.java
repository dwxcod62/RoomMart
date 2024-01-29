package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.UserInformationDAO;
import com.codebrew.roommart.dto.UserInformation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UpdateProfileServlet", value = "/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    public static final String ERROR = "/pages/renter/renter-profile-update.jsp";
    public static final String SUCCESS = "/pages/renter/renter-profile-update.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        request.setCharacterEncoding("UTF-8");
//        Account acc = new Account();
        UserInformation accountInfos = new UserInformation();
        request.setAttribute("uri", request.getRequestURI());
        try {
//            HttpSession session = request.getSession();
//            acc = (Account) session.getAttribute("USER");
            UserInformation accountInfor = new UserInformationDAO().getAccountInformationById(1);
//            int accId = acc.getAccId();
            String profileName = request.getParameter("new-name").equals("") ? accountInfor.getFullname() : request.getParameter("new-name");
            String profileEmail = request.getParameter("new-email").equals("") ? accountInfor.getEmail() : request.getParameter("new-email");
            accountInfor.setBirthday(request.getParameter("new-birthday"));
            boolean sex = Boolean.valueOf(request.getParameter("new-sex"));
            String profilePhone = request.getParameter("new-phone").equals("") ? accountInfor.getPhone() : request.getParameter("new-phone");
            String profileAddress = request.getParameter("new-address").equals("") ? accountInfor.getAddress() : request.getParameter("new-address");
            String profileCCCD = request.getParameter("new-cccd").equals("") ? accountInfor.getCccd() : request.getParameter("new-cccd");

            accountInfos = UserInformation.builder()
                    .fullname(profileName)
                    .email(profileEmail)
                    .phone(profilePhone)
                    .sex(sex)
                    .address(profileAddress)
                    .cccd(profileCCCD)
                    .build();

            boolean checkUpdateProfile = new UserInformationDAO().updateProfileByAccId(accountInfos, 1);
            if (checkUpdateProfile) {
                request.setAttribute("MES", "Cập nhật thành công!");
                List<UserInformation> userInfo= new UserInformationDAO().getListAccountInformationById(1);
                request.setAttribute("ACC_INFO", userInfo);
                url = SUCCESS;
            } else {
                request.setAttribute("MES", "Cập nhật thất bại!");
                request.setAttribute("Error", "Somethings Wrong!");
            }
            response.sendRedirect(request.getContextPath() + "/ProfileRenter");
        } catch (Exception e) {
            log("Error at UpdateHostel: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private Date parseDateOrDefault(String dateStr, Date defaultValue) {
        if (!dateStr.isEmpty()) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                log("Error parsing date: " + e.toString());
                return defaultValue;
            }
        }
        return defaultValue;
    }
}