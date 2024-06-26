package com.codebrew.roommart.servlets.OwnerServlets.Hostel.HostelService;

import com.codebrew.roommart.dao.NotificationDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelServiceDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.ServiceInfoDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.ServiceInfo;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UpdateServiceServlet", value = "/UpdateServiceServlet")
public class UpdateServiceServlet extends HttpServlet {
    private final String FAIL = "detailHostel?hostelID=";
    private final String ERROR = "error-page";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = ERROR;
        HandlerStatus handlerStatus = null;
        HttpSession session = request.getSession(false);
        Account owner = (Account) session.getAttribute("USER");
        int ownerId = owner.getAccId();
        try {
            url = FAIL;
            int hostelId = Integer.parseInt(request.getParameter("hostel-id"));
            String[] servicesIdStr = request.getParameterValues("update-service-id");
            String[] servicesPriceStr = request.getParameterValues("update-service-price");
            HostelServiceDAO hostelServiceDAO = new HostelServiceDAO();

            List<HostelService> currentHostelServiceList = hostelServiceDAO.getCurrentListServicesOfAHostel(hostelId); // lấy lisService hiện tại của hostel
            boolean checkUpdate = hostelServiceDAO.updateStatusOfListHostelServices(0, currentHostelServiceList); // remove dữ liệu cũ, set status = 0
            if (!checkUpdate) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Vui lòng thử lại sau!").build());
                url += hostelId;
            } else {
                List<HostelService> hostelServiceList = new ArrayList<>(); // thêm các service vào trong list HostelService
                for (int i = 0; i < servicesIdStr.length; i++) {
                    hostelServiceList.add(HostelService.builder()
                            .serviceID(Integer.parseInt(servicesIdStr[i]))
                            .servicePrice(Integer.parseInt(servicesPriceStr[i])).build());
                }

                checkUpdate = hostelServiceDAO.insertListServicesIntoHostel(hostelServiceList, hostelId); // thêm list HostekService mới vào db, status = 1
                url += hostelId;
                if (checkUpdate) {

                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(true)
                            .content("Cập nhật dịch vụ thành công!").build());

                    String content = "Cập nhật dịch vụ: \n";
                    List<ServiceInfo> serviceInfoList = new ServiceInfoDAO().getServicesOfHostel(hostelId);
                    for (ServiceInfo item: serviceInfoList) {
                        content += item.getServiceName() + ": " + item.getServicePrice() + "VND || ";
                    }

//                    request.setAttribute("noti-hostel-id", hostelId);
//                    request.setAttribute("noti-title", "Thông báo cập nhật dịch vụ");
                    String title = "Thông báo cập nhật dịch vụ";
//                    request.setAttribute("noti-content", content);
                    if (new HostelDAO().checkOwnerHostel(ownerId)) {
                        int notiId = new NotificationDao().creatNotification(ownerId, hostelId, title, content);
                    }

                } else {
                    request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                            .status(false)
                            .content("Cập nhật dịch vụ thất bại!").build());
                }
            }
        } catch (Exception e) {
            log("Error at UpdateServiceServlet: " + e.toString());
        } finally {
            System.out.println(url);
             response.sendRedirect(url);
        }
    }
}
