package com.codebrew.roommart.servlets.OwnerServlets.Hostel;

import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceDAO;
import com.codebrew.roommart.dao.ServiceInfoDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Services;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddHostelServlet", value = "/add-new-hostel")
public class AddHostelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceInfoDAO sid = new ServiceInfoDAO();
        List<Services> services = sid.getAllServices();
        request.setAttribute("serviceList",services);
        request.getRequestDispatcher("pages/owner/hostel/add-new-hostel.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String url = "hostel-page";

        List<HostelService> hostelServiceList = new ArrayList<>();
        Account acc;
        Map<String, Services> servicesList = new ServiceDAO().getAll();

        try {
            req.setCharacterEncoding("UTF-8");
            HttpSession session = req.getSession();
            acc = (Account) session.getAttribute("USER");
            HostelDAO dao = new HostelDAO();

            int accountId = acc.getAccId();
            String hostelName = req.getParameter("hostel-name");
            String hostelAddress = req.getParameter("hostel-address");
            String hostelProvince = req.getParameter("hostel-province");
            String hostelDistrict = req.getParameter("hostel-district");
            String hostelWard = req.getParameter("hostel-ward");

            //electric
            int electricityPrice = Integer.parseInt(req.getParameter("hostel-electric"));
            hostelServiceList.add(HostelService.builder()
                    .serviceID(servicesList.get("Điện").getServiceID())
                    .servicePrice(electricityPrice).build());

            //water
            int waterPrice = Integer.parseInt(req.getParameter("hostel-water"));
            hostelServiceList.add(HostelService.builder()
                    .serviceID(servicesList.get("Nước").getServiceID())
                    .servicePrice(waterPrice).build());

            //wifi
            int internetPrice = Integer.parseInt(req.getParameter("hostel-wifi"));
            if (internetPrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Wifi").getServiceID())
                        .servicePrice(internetPrice).build());
            }

            //Management
            int managementPrice = Integer.parseInt(req.getParameter("hostel-manage"));
            if (managementPrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Phí quản lý").getServiceID())
                        .servicePrice(managementPrice).build());
            }

            //Vehicle
            int vehiclePrice = Integer.parseInt(req.getParameter("hostel-vehicle"));
            if (vehiclePrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Phí giữ xe").getServiceID())
                        .servicePrice(vehiclePrice).build());
            }

            //cleaning
            int cleaningPrice = Integer.parseInt(req.getParameter("hostel-cleaning"));
            if (cleaningPrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Phí vệ sinh").getServiceID())
                        .servicePrice(cleaningPrice).build());
            }

            Hostel hostel = Hostel.builder()
                    .hostelOwnerAccountID(accountId)
                    .hostelName(hostelName)
                    .address(hostelAddress)
                    .ward(hostelWard)
                    .district(hostelDistrict)
                    .city(hostelProvince).build();
            int hostelId = dao.addHostel(hostel, hostelServiceList);

            if (hostelId >= 0) {
                req.setAttribute("HOSTEL_ID", hostelId);

                List<Hostel> listHostel = new HostelDAO().getHostelByOwnerId(accountId);
                req.setAttribute("LIST_HOSTEL", listHostel);

                Map<Integer, Integer> ListNumberTotalRoomsOfHostel = new HashMap<>();
                IRoomDAO roomDAO = new RoomDAO();
                if (listHostel.size() > 0) {
                    for (Hostel hostel1 : listHostel) {
                        ListNumberTotalRoomsOfHostel.put(hostel1.getHostelID(), roomDAO.getNumberRoomSpecificHostel(hostel1.getHostelID()));
                    }
                    req.setAttribute("LIST_TOTAL_ROOM", ListNumberTotalRoomsOfHostel);
                }

                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Tạo khu trọ thành công!").build());
            } else {
                req.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Tạo khu trọ thất bại!").build());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            req.getRequestDispatcher("hostel-page").forward(req, response);
//            response.sendRedirect("owner-hostel-list");
        }
    }
}
