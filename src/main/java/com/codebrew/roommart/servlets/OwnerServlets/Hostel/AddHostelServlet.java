package com.codebrew.roommart.servlets.OwnerServlets.Hostel;


import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.HashMap;

import java.util.Collection;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "AddHostelServlet", value = "/add-new-hostel")
@MultipartConfig(
        fileSizeThreshold = 1024 * 3*1024,  // Kích thước tệp tối thiểu trước khi lưu vào bộ nhớ tạm thời, đơn vị byte
        maxFileSize = 1024 * 1024*10,       // Kích thước tệp tối đa cho một yêu cầu, đơn vị byte
        maxRequestSize = 1024 * 1024*20    // Kích thước tệp tối đa cho một yêu cầu, đơn vị byte
)
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
        Part hostelNamePart = req.getPart("hostel-name");
        Part hostelAddressPart = req.getPart("hostel-address");
        Part hostelProvincePart = req.getPart("hostel-province");
        Part hostelDistrictPart = req.getPart("hostel-district");
        Part hostelWardPart = req.getPart("hostel-ward");

        Part electricityPricePart = req.getPart("hostel-electric");
        Part waterPricePart = req.getPart("hostel-water");
        Part internetPricePart = req.getPart("hostel-wifi");
        Part managementPricePart = req.getPart("hostel-manage");
        Part vehiclePricePart = req.getPart("hostel-vehicle");
        Part cleaningPricePart = req.getPart("hostel-cleaning");

        try {
            req.setCharacterEncoding("UTF-8");
            HttpSession session = req.getSession();
            acc = (Account) session.getAttribute("USER");
            HostelDAO dao = new HostelDAO();

            int accountId = acc.getAccId();
            String hostelName = String.valueOf(getPartValue(hostelNamePart));
            String hostelAddress = String.valueOf(getPartValue(hostelAddressPart));
            String hostelProvince = String.valueOf(getPartValue(hostelProvincePart));
            String hostelDistrict = String.valueOf(getPartValue(hostelDistrictPart));
            String hostelWard = String.valueOf(getPartValue(hostelWardPart));

            //electric
            int electricityPrice = Integer.parseInt(getPartValue(electricityPricePart));
            hostelServiceList.add(HostelService.builder()
                    .serviceID(servicesList.get("Điện").getServiceID())
                    .servicePrice(electricityPrice).build());

            //water
            int waterPrice = Integer.parseInt(getPartValue(waterPricePart));
            hostelServiceList.add(HostelService.builder()
                    .serviceID(servicesList.get("Nước").getServiceID())
                    .servicePrice(waterPrice).build());

            //wifi
            int internetPrice = Integer.parseInt(getPartValue(internetPricePart));
            if (internetPrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Wifi").getServiceID())
                        .servicePrice(internetPrice).build());
            }

            //Management
            int managementPrice = Integer.parseInt(getPartValue(managementPricePart));
            if (managementPrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Phí quản lý").getServiceID())
                        .servicePrice(managementPrice).build());
            }

            //Vehicle
            int vehiclePrice = Integer.parseInt(getPartValue(vehiclePricePart));
            if (vehiclePrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Phí giữ xe").getServiceID())
                        .servicePrice(vehiclePrice).build());
            }

            //cleaning
            int cleaningPrice = Integer.parseInt(getPartValue(cleaningPricePart));
            if (cleaningPrice > 0) {
                hostelServiceList.add(HostelService.builder()
                        .serviceID(servicesList.get("Phí vệ sinh").getServiceID())
                        .servicePrice(cleaningPrice).build());
            }
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dqp6vdayn",
                    "api_key", "527664667972471",
                    "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                    "secure", true));
            cloudinary.config.secure = true;
            System.out.println(cloudinary.config.cloudName);
            // Set your Cloudinary credentials
            List<String> roomFiles = new ArrayList<>();

            List<Part> fileParts = req.getParts().stream().filter(part -> "fileImage".equals(part.getName())).collect(Collectors.toList());
            System.out.println("fileParts:" + fileParts);
            if (!fileParts.isEmpty()){
                System.out.println(getFileName(fileParts.get(0)));
                Collection<Part> parts = req.getParts().stream()
                        .filter(part -> "fileImage".equals(part.getName()) && part.getSize() > 0)
                        .collect(Collectors.toList());
                for (Part part : fileParts) {
                    String fileName = getFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
                        InputStream inputStream = part.getInputStream();
                        byte[] fileBytes = inputStream.readAllBytes();
                        Map<String, Object> uploadResult = cloudinary.uploader().upload(fileBytes, ObjectUtils.emptyMap());
                        String imageUrl = (String) uploadResult.get("url");
                        roomFiles.add(imageUrl);
                        System.out.println("Uploaded image URL: " + imageUrl);
                        // Lưu URL của hình ảnh vào cơ sở dữ liệu hoặc thực hiện các thao tác khác.
                    }
                }}else {
                roomFiles = null;
            }

            Hostel hostel = Hostel.builder()
                    .hostelOwnerAccountID(accountId)
                    .hostelName(hostelName)
                    .address(hostelAddress)
                    .ward(hostelWard)
                    .district(hostelDistrict)
                    .imgUrl(roomFiles)
                    .status(1) // dang duyet
                    .city(hostelProvince).build();
            int hostelId = dao.addHostel(hostel, hostelServiceList); // thêm mới hostel vào db

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
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return null;
    }
    private String getPartValue(Part part) throws IOException {
        if (part == null) {
            return null;
        }
        // Đọc dữ liệu từ phần multipart và trả về dạng chuỗi
        StringBuilder value = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                value.append(line);
            }
        }
        return value.toString();
    }
}
