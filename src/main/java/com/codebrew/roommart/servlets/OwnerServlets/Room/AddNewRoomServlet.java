package com.codebrew.roommart.servlets.OwnerServlets.Room;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.codebrew.roommart.dao.HostelDao;
import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.HandlerStatus;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "AddNewRoomServlet", value = "/AddNewRoomServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 3*1024,  // Kích thước tệp tối thiểu trước khi lưu vào bộ nhớ tạm thời, đơn vị byte
        maxFileSize = 1024 * 1024*10,       // Kích thước tệp tối đa cho một yêu cầu, đơn vị byte
        maxRequestSize = 1024 * 1024*20    // Kích thước tệp tối đa cho một yêu cầu, đơn vị byte
)
public class AddNewRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "AddRoomPage";
        try {
            int hostelId = Integer.parseInt(request.getParameter("hostelID"));

            System.out.println("hostelId="+hostelId);
            request.setAttribute("hid",hostelId);
//            Hostel hostel = new HostelDAO().getHostelById(hostelId);
//            System.out.println("->hostel: "+ hostel);
//            if (hostel != null) {
//                url = "AddRoomPage";
//                request.setAttribute("hostel", hostel);
//            }
        } catch (Exception e) {
            log("Error at AddRoomPageServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String url = "login.jsp";

        Part quantityRoomPart = request.getPart("room-quantity");
        Part capacityPart = request.getPart("room-capacity");
        Part roomAreaPart = request.getPart("room-area");
        Part atticPart = request.getPart("room-floor");
        Part restroomsPart = request.getPart("room-toilet");
        Part restroomStatusPart = request.getPart("room-toilet-status");
        Part windowsPart = request.getPart("room-window");
        Part windowsStatusPart = request.getPart("room-window-status");
        Part airConditionsPart = request.getPart("room-air-conditioner");
        Part airConditionsStatusPart = request.getPart("room-air-conditioner-status");
        Part roomDoorsPart = request.getPart("room-door");
        Part roomDoorsStatusPart = request.getPart("room-door-status");
        Part roomPricePart = request.getPart("room-price");


        // Convert parts to appropriate data types
        int quantityRoom = Integer.parseInt(getPartValue(quantityRoomPart));
        System.out.println("quantity: "+quantityRoom);
        int capacity = Integer.parseInt(getPartValue(capacityPart));
        double roomArea = Double.parseDouble(getPartValue(roomAreaPart));
        int attic = Integer.parseInt(getPartValue(atticPart));
        int restrooms = Integer.parseInt(getPartValue(restroomsPart));
        int restroomStatus = Integer.parseInt(getPartValue(restroomStatusPart));
        int windows = Integer.parseInt(getPartValue(windowsPart));
        int windowsStatus = Integer.parseInt(getPartValue(windowsStatusPart));
        int airConditions = Integer.parseInt(getPartValue(airConditionsPart));
        int airConditionsStatus = Integer.parseInt(getPartValue(airConditionsStatusPart));
        int roomDoors = Integer.parseInt(getPartValue(roomDoorsPart));
        int roomDoorsStatus = Integer.parseInt(getPartValue(roomDoorsStatusPart));
        int price = Integer.parseInt(getPartValue(roomPricePart));


        RoomDao roomDao = new RoomDao();

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqp6vdayn",
                "api_key", "527664667972471",
                "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                "secure", true));
        cloudinary.config.secure = true;
        System.out.println(cloudinary.config.cloudName);
        // Set your Cloudinary credentials
        List<String> roomFiles = new ArrayList<>();

        List<Part> fileParts = request.getParts().stream().filter(part -> "fileImage".equals(part.getName())).collect(Collectors.toList());
        System.out.println("fileParts:" + fileParts);
        if (!fileParts.isEmpty()){
            System.out.println(getFileName(fileParts.get(0)));
            Collection<Part> parts = request.getParts().stream()
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

        try {
            int hostelId = Integer.parseInt(request.getParameter("hid"));


//            Hostel hostel = new HostelDAO().getHostelById(hostelId);
            request.setAttribute("hid", hostelId);
            if (quantityRoom > 1) {
                String tempImg = "https://res.cloudinary.com/dqp6vdayn/image/upload/v1707647165/What-is-a-404-error-code_lu1xgy.png";
                for (int i = 0; i < quantityRoom; i++) {
                    boolean isSuccess = roomDao.addNewManyRooms(hostelId, capacity, roomArea, attic, 1,
                            tempImg,price,restrooms, restroomStatus,
                            windows, windowsStatus,
                            roomDoors, roomDoorsStatus,
                            airConditions, airConditionsStatus);
                    System.out.println(isSuccess);
                    url = "AddRoomPage";
                    if (isSuccess) {
                        request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                                .status(true)
                                .content("Bạn đã thêm " + quantityRoom + " phòng mới thành công!").build());
                    } else {
                        request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                                .status(false)
                                .content("Đã có lỗi xảy ra! Thêm phòng mới thất bại!").build());
                    }
                }
            } else {
                System.out.println("add one room");
                int roomNumber = Integer.parseInt(request.getParameter("room-name"));

//                boolean isSuccess = roomDao.addNewRoom(hostelId, roomNumber, capacity, roomArea, attic, 1,
//                        restrooms, restroomStatus,
//                        windows, windowsStatus,
//                        roomDoors, roomDoorsStatus,
//                        airConditions, airConditionsStatus);
                boolean isSuccess = roomDao.addNewRoom(hostelId, roomNumber, capacity, roomArea, attic, 1,roomFiles,price
                        , restrooms, restroomStatus,
                        windows, windowsStatus,
                        roomDoors, roomDoorsStatus,
                        airConditions, airConditionsStatus);
                url = "AddRoomPage";
                if (isSuccess) {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Bạn đã thêm " + quantityRoom + " phòng mới thành công!").build());
                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Thêm phòng mới thất bại!").build());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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