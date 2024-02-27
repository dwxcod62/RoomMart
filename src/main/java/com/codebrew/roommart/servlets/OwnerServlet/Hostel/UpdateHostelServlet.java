package com.codebrew.roommart.servlets.OwnerServlet.Hostel;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.OwnerDTO.HandlerStatus;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateHostelServlet", value = "/UpdateHostelServlet")
public class UpdateHostelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int hostelID = Integer.parseInt(request.getParameter("hostelID"));
            Hostel hostel = new HostelDAO().getHostelById(hostelID);
            request.setAttribute("HOSTEL", hostel);
            request.getRequestDispatcher("pages/owner/hostel/update-hostel.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String url = "owner-hostel-list";
        req.setCharacterEncoding("UTF-8");
        try {
            int hostelID = Integer.parseInt(req.getParameter("hostelID"));
            String hostelName = req.getParameter("hostel-name");
            String hostelAddress = req.getParameter("hostel-address");
            String hostelWard = req.getParameter("hostel-ward");
            String hostelDistrict = req.getParameter("hostel-district");
            String hostelProvince = req.getParameter("hostel-province");

            HostelDAO dao = new HostelDAO();
            Hostel newHostel = Hostel.builder()
                    .hostelName(hostelName)
                    .address(hostelAddress)
                    .ward(hostelWard)
                    .district(hostelDistrict)
                    .city(hostelProvince).build();
            boolean checkUpdate = dao.updateHostel(newHostel, hostelID);
            if (checkUpdate) {
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Cập nhật thông tin khu trọ thành công!").build());
            } else {
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Cập nhật thông tin khu trọ thất bại!").build());
            }
        } catch (Exception e) {
            log("Error at UpdateHostel: " + e.toString());
        } finally {
            response.sendRedirect(url);
        }
    }
}
