package com.codebrew.roommart.servlets.OwnerServlet.Hostel;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelServiceDAO;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.HandlerStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AddNewServiceServlet", value = "/add-new-service")
public class AddNewServiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "detailHostel?hostelID=";

        try {
            int hostelId = Integer.parseInt(request.getParameter("hostel-id"));
            int serviceId = Integer.parseInt(request.getParameter("service-id"));
            int servicePrice = Integer.parseInt(request.getParameter("service-price"));

            List<HostelService> list = new HostelServiceDAO().getCurrentListServicesOfAHostel(hostelId);
            boolean checkUpdate = new HostelServiceDAO().updateStatusOfListHostelServices(false, list);

            if (!checkUpdate) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
            } else {
                list.add(HostelService.builder()
                        .serviceID(serviceId)
                        .hostelID(hostelId)
                        .servicePrice(servicePrice).build());

                boolean checkInsert = new HostelServiceDAO().insertListServicesIntoHostel(list, hostelId);
                if (checkInsert) {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Thêm dịch vụ mới thành công!").build());
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Thêm dịch vụ mới thất bại!").build());
                }
            }
            url += hostelId;

        } catch (Exception e) {
            log("Error at UpdateServiceServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
