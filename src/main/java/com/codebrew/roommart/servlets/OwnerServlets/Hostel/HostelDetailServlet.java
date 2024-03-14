package com.codebrew.roommart.servlets.OwnerServlets.Hostel;


import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceInfoDAO;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HostelDetailServlet", value = "/detailHostel")
public class HostelDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "listHostel";
        String ERROR = "error-page";
        try {
            Account acc;
            HttpSession session = request.getSession();
            acc = (Account) session.getAttribute("USER");
            int accountId = acc.getAccId();

            int hostelId = Integer.parseInt(request.getParameter("hostelID"));

            Hostel hostel = new HostelDAO().getHostelByIdWithConstraint(hostelId, accountId); //thông tin hostel (để xem chi tiết)

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
            e.printStackTrace();
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
