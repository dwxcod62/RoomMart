package com.codebrew.roommart.servlets.ContractServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.SystemDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.EmailUtils;
import com.codebrew.roommart.utils.EncodeUtils;
import org.json.JSONObject;

@WebServlet(name = "CreateContractServlet", value = "/CreateContractServlet")
public class CreateContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "error";
        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");
        String data = request.getParameter("data");

        if (acc == null){
            url = "login";
            Status status = Status.builder()
                            .status(false)
                            .content("Vui lòng đăng nhập để sử dụng").build();
            session.setAttribute("qwerty", data);
            request.setAttribute("RESPONSE_MSG", status);
        }

        System.out.println("d");

        try {
            if (data != null) {
                AccountDao dao = new AccountDao();
                SystemDao sysDao = new SystemDao();

                String decodeString = EncodeUtils.decodeString(data);

                String renter_mail = (decodeString.split("&")[0]).split("=")[1];
                String owner_mail = (decodeString.split("&")[1]).split("=")[1];

                UserInformation owner_info = dao.getInfoByMailForContact(owner_mail);
                UserInformation renter_info = dao.getInfoByMailForContact(renter_mail);

                JSONObject jsonObject = sysDao.getContractInformationByEmail(owner_mail, renter_mail);

                if ( acc.getRole() == 1 ){
                    renter_info.setCccd(maskCccd(renter_info.getCccd()));
                    renter_info.setPhone(maskCccd(renter_info.getPhone()));
                }

                if ( acc.getRole()  == 3){
                    request.setAttribute("OWNER_SIGN", jsonObject.getString("owner_sign"));
                }

                String between = countYear(jsonObject.getString("room_start_date"), jsonObject.getString("room_end_date"));

                request.setAttribute("USER_CONTRACT", acc.getRole());
                request.setAttribute("OWNER_INFO", owner_info);
                request.setAttribute("RENTER_INFO", renter_info);
                request.setAttribute("room_start_date", formatDate(jsonObject.getString("room_start_date")));
                request.setAttribute("room_end_date", formatDate(jsonObject.getString("room_end_date")));
                request.setAttribute("between", between);
                request.setAttribute("room_fee", jsonObject.getInt("room_fee"));
                request.setAttribute("payment_term", jsonObject.getInt("payment_term"));
                request.setAttribute("room_deposit", jsonObject.getInt("room_deposit"));

                url = "contract-details";

            }
        } catch ( Exception e){
            e.printStackTrace();
        }

        request.getRequestDispatcher(url).forward(request, response);
    }

    public static String formatDate(String dateString) {
        String[] lst_date = dateString.split("-");

        return "Ngày " + lst_date[2] + " Tháng " + lst_date[1] + " Năm " + lst_date[0];
    }

    public static String countYear(String startdate, String endate) {
        LocalDate startDate = LocalDate.parse(startdate);
        LocalDate endDate = LocalDate.parse(endate);

        Period period = Period.between(startDate, endDate);
        int totalDays = period.getDays();
        int totalMonths = period.getMonths();
        int totalYears = period.getYears();

        int years = totalDays / 365;
        int months = (totalDays % 365) / 30;
        int days = (totalDays % 365) % 30;

        int totalYearsCorrected = totalYears + years;
        int totalMonthsCorrected = totalMonths + months;
        int totalDaysCorrected = days;

        return  totalYearsCorrected + " năm, " + totalMonthsCorrected + " tháng và " + totalDaysCorrected + " ngày";
    }

    public static String maskCccd(String cccd) {
        String prefix = cccd.substring(0, 4);

        String asterisks = "*".repeat(cccd.length() - 4);

        return prefix + asterisks;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "login";
        HttpSession session = request.getSession(true);
        Account acc = (Account) session.getAttribute("USER");

        if (acc != null ){
            JSONObject jsonObject = new JSONObject();

            String renter_email = request.getParameter("room-email");

            // RENTER
            jsonObject.put("renter_mail", renter_email);

            // OWNER
            jsonObject.put("owner_mail", acc.getEmail());


            // PROPERTIES
            jsonObject.put("room_start_date", request.getParameter("room-startdate"));
            jsonObject.put("room_end_date", request.getParameter("room-enddate"));
            jsonObject.put("room_electric", request.getParameter("room-electric"));
            jsonObject.put("room_water", request.getParameter("room-water"));
            jsonObject.put("room_fee", request.getParameter("room-fee"));
            jsonObject.put("payment_term", request.getParameter("payment-term"));
            jsonObject.put("room_deposit", request.getParameter("room-deposit"));
            jsonObject.put("fixed_years", request.getParameter("fixed-years"));
            jsonObject.put("percentage_increase", request.getParameter("percentage-increase"));

            SystemDao dao = new SystemDao();
            dao.updateContractOwnerSide(jsonObject);

            String data = "renter=" + renter_email + "&owner=" + acc.getEmail();
            String encode_object_string = EncodeUtils.encodeString(data);

            url = "createContract?data=" + encode_object_string;
            response.sendRedirect(url);

        } else {
            Status status = Status.builder()
                            .status(false)
                            .content("Vui lòng đăng nhập trước khi dùng dịch vụ")
                            .build();
            request.setAttribute("RESPONSE_MSG", status);
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}