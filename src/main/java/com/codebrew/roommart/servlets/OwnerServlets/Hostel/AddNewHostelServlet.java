package com.codebrew.roommart.servlets.OwnerServlets.Hostel;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.ServiceInfoDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Services;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddNewHostelServlet", value = "/add-new-hostel")
public class AddNewHostelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceInfoDAO sid = new ServiceInfoDAO();
        List<Services> services = sid.getAllServices();
        request.setAttribute("serviceList",services);
        request.getRequestDispatcher("pages/owner/hostel/add-new-hostel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        Account acc;
        try {
            req.setCharacterEncoding("UTF-8");
            HttpSession session = req.getSession();
            acc = (Account) session.getAttribute("USER");

            int accountId = acc.getAccId();
            String hostelName = req.getParameter("hostel-name");
            String hostelAddress = req.getParameter("hostel-address");
            String hostelProvince = req.getParameter("hostel-province");
            String hostelDistrict = req.getParameter("hostel-district");
            String hostelWard = req.getParameter("hostel-ward");

            new HostelDAO().addNewHostel(new Hostel(accountId, hostelName, hostelAddress, hostelWard, hostelDistrict, hostelProvince));
//            req.setAttribute("HOSTEL_ID", hostelId);
            req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                    .status(true)
                    .content("Tạo khu trọ thành công!").build());
        } catch (Exception e) {
            log("Error at AddHostel: " + e.toString());
        } finally {
            req.getRequestDispatcher("hostel-page").forward(req, response);
        }
    }
}
