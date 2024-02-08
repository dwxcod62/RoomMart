package com.codebrew.roommart.servlets.OwnerServlet.Hostel;


import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceInfoDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.ServiceInfo;
import com.codebrew.roommart.dto.Services;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HostelDetailServlet", value = "/detailHostel")
public class HostelDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "list-hostels";
        String ERROR = "error-page";
        Account acc;
        try {
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
//        int accountId = acc.getAccountId();

            int hostelId = Integer.parseInt(request.getParameter("hostelID"));

            Hostel hostel = new HostelDAO().getHostelByIdWithConstraint(hostelId, 1); //thông tin hostel (để xem chi tiết) // accountId ảo

            RoomDAO roomDao = new RoomDAO();

            if (hostel == null) {
                url = ERROR;
                System.out.println("hostel null");
                return;
            }

            List<Room> rooms = roomDao.getListRoomsByHostelId(hostelId);  // list các room của hostel đó
            int roomQuantity = roomDao.getNumberRoomSpecificHostel(hostelId); // tổng số phòng của hostel đó

            List<ServiceInfo> serviceList = new ServiceInfoDAO().getServicesOfHostel(hostelId); // thông tin dịch vụ của hostel đó

            List<Services> listServicesNotInHostel = new ServiceDAO().getListServicesNotInHostel(hostelId); // danh sách service mà chủ trọ có thể chọn để thêm vào các dịch vụ hiện có của khu trọ của họ (các dịch vụ này sẽ không trùng lăp với dịch vụ đang hiện có của khu trọ đó) => when click modal add new service
            url = "pages/owner/hostel/hostel-detail.jsp";
            request.setAttribute("hostel", hostel);
            session.setAttribute("hostel", hostel);
            request.setAttribute("roomList", rooms);
            request.setAttribute("serviceInfo", serviceList);
            request.setAttribute("roomQuantity", roomQuantity);
            request.setAttribute("services", listServicesNotInHostel);
            session.setAttribute("CURRENT_PAGE", "hostel");
        } catch (Exception e) {
            log("Error at HostelDetailServlet: " + e.toString());
        } finally {
            if (ERROR.equalsIgnoreCase(url))
                response.sendRedirect(url);
            else
                request.getRequestDispatcher(url).forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
