package com.codebrew.roommart.servlets.AdminServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.codebrew.roommart.dao.InfrastructureItemDao;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.InfrastructureItem;

@WebServlet(name = "AdminCreateNewInfrastructureServlet", value = "/AdminCreateNewInfrastructureServlet")
public class AdminCreateNewInfrastructureServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            HandlerStatus handlerStatus;
            String infrastructureName = request.getParameter("infrastructureName").trim();
            InfrastructureItem infrastructureItem = new InfrastructureItemDao().getInfrastructureByName(infrastructureName);
            if (infrastructureItem == null) {
                boolean createResult = new InfrastructureItemDao().createNewInfrastructureItem(infrastructureName);
                if (createResult) {
                    handlerStatus = HandlerStatus.builder()
                            .status(true)
                            .content("Tạo mới cơ sở vật chất thành công!").build();
                } else {
                    handlerStatus = HandlerStatus.builder()
                            .status(false)
                            .content("Đã có lỗi xảy ra! Tạo mới cơ sở vật chất thất bại!").build();
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
