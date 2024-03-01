package com.codebrew.roommart.servlets.OwnerServlets;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.Hostel;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;





@WebServlet(name = "UpdateRoomServlet", value = "/UpdateRoomServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 10,  // Kích thước tệp tối thiểu trước khi lưu vào bộ nhớ tạm thời, đơn vị byte
        maxFileSize = 1024 * 300,       // Kích thước tệp tối đa cho một yêu cầu, đơn vị byte
        maxRequestSize = 1024 * 1024    // Kích thước tệp tối đa cho một yêu cầu, đơn vị byte
)
public class UpdateRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "AddRoomPage";
        int roomID = Integer.parseInt(request.getParameter("rid"));
        System.out.println(roomID);
//        Part quantityRoomPart = request.getPart("roomID");
//        int quantityRoom = Integer.parseInt(getPartValue(quantityRoomPart));
        request.setAttribute("rid",roomID);
        RoomDao roomDAO = new RoomDao();
        Room r = roomDAO.getRoomInformationByRoomId(roomID);
        System.out.println(r);
        request.setAttribute("r",r);
        request.getRequestDispatcher(url).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int roomID = Integer.parseInt(request.getParameter("roomID"));
        int roomNumber = Integer.parseInt(request.getParameter("room-name"));
        int capacity = Integer.parseInt(request.getParameter("room-capacity"));
        double roomArea = Double.parseDouble(request.getParameter("room-area"));
        int attic = Integer.parseInt(request.getParameter("room-floor"));
        HttpSession session = request.getSession();
        int hostelID = ((Hostel) session.getAttribute("hostel")).getHostelID();

//        request.setAttribute("hostelID",hostelID);
//        request.setAttribute("roomID",roomID);
        String url = "ownerRoomDetail?roomID="+roomID+"&hostelID="+hostelID; // thay url cho nay

        RoomDao roomDao = new RoomDao();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dqp6vdayn",
                "api_key", "527664667972471",
                "api_secret", "HzMyAcz7DKbWinMpZEsLe64XkUo",
                "secure", true));
        cloudinary.config.secure = true;
        System.out.println(cloudinary.config.cloudName);

        List<String> roomFiles = new ArrayList<>();

        List<Part> fileParts = request.getParts().stream()
                .filter(part -> "fileImage".equals(part.getName()) && part.getSize() > 0) // Check if the part is not empty
                .collect(Collectors.toList());
        System.out.println("filesParts: "+ fileParts);


//            System.out.println(getFileName(fileParts.get(0)));
//            Collection<Part> parts = request.getParts().stream()
//                    .filter(part -> "fileImage".equals(part.getName()) && part.getSize() > 0)
//                    .collect(Collectors.toList());
        if (fileParts != null){
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
            }
        }else {
            roomFiles = null;
        }
        System.out.println("room files: "+roomFiles);
        try {
            boolean isSuccessUpdate = roomDao.updateRoom(roomID, roomNumber, capacity, roomArea, attic,roomFiles);

            if (isSuccessUpdate) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Cập nhật thông tin phòng thành công!").build());
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Cập nhật thông tin phòng thất bại!").build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            request.getRequestDispatcher(url).forward(request, response);
            response.sendRedirect(url);
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
}
