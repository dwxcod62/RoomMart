package com.codebrew.roommart.servlets.AdminServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.codebrew.roommart.dao.ServicesDao;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Services;


@WebServlet(name = "AdminCreateNewServiceServlet", value = "/AdminCreateNewServiceServlet")
public class AdminCreateNewServiceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            //Lỗi tiếng việt
            request.setCharacterEncoding("UTF-8");
            HandlerStatus handlerStatus;
            String serviceName = request.getParameter("serviceName").trim();
            String serviceUnit = request.getParameter("serviceUnit").trim();

            Services service = new ServicesDao().getServiceByName(serviceName);
            if (service == null) {
                boolean insertResult = new ServicesDao().createNewService(Services.builder().serviceName(serviceName).unit(serviceUnit).build());
                if (insertResult) {
                    handlerStatus = HandlerStatus.builder()
                            .status(true)
                            .content("Tạo mới dịch vụ thành công!").build();
                } else {
                    handlerStatus = HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Tạo mới dịch vụ thất bại!").build();
                }
            } else {
                handlerStatus = HandlerStatus.builder()
                        .status(false)
                        .content("Tạo mới thất bại! Tên dịch vụ đã tồn tại! Vui lòng thử lại tên khác!").build();
            }
            request.setAttribute("RESPONSE_MSG", handlerStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("manage-service").forward(request, response);
        }
    }
}
