package com.codebrew.roommart.servlets.AdminServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.codebrew.roommart.dao.InfrastructureDao;
import com.codebrew.roommart.dao.InfrastructureItemDao;
import com.codebrew.roommart.dao.ServicesDao;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.InfrastructureItem;

@WebServlet(name = "AdminUpdateInfrastructureServlet", value = "/AdminUpdateInfrastructureServlet")
public class AdminUpdateInfrastructureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            HandlerStatus handlerStatus;
            int infrastructureId = Integer.parseInt(request.getParameter("infrastructureId"));
            String infrastructureName = request.getParameter("infrastructureName").trim();
            InfrastructureItem infrastructureItem = new InfrastructureItemDao().getInfrastructureByName(infrastructureName);
            if (infrastructureItem == null) {
                boolean updateResult = new InfrastructureItemDao().updateInfrastructureItem(
                        InfrastructureItem.builder().idInfrastructureItem(infrastructureId).infrastructureName(infrastructureName).build()
                );
                if (updateResult) {
                    handlerStatus = HandlerStatus.builder()
                            .status(true)
                            .content("Cập nhận thông tin cơ sở vật chất thành công!").build();
                } else {
                    handlerStatus = HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Cập nhận thông tin cơ sở vật chất thất bại!").build();
                }
            } else {
                handlerStatus = HandlerStatus.builder()
                        .status(false)
                        .content("Loại cơ sở vật chất này đã tồn tại! Vui lòng thử lại với tên khác!").build();
            }
            request.setAttribute("RESPONSE_MSG", handlerStatus);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("manage-infrastructure").forward(request, response);
        }
    }
}
