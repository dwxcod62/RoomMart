package com.codebrew.roommart.servlets.AdminServlets;

import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.HandlerStatus;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "UpdateHostelStatusServlet", value = "/UpdateHostelStatusServlet")
public class UpdateHostelStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("manage-license-page");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String url = "manage-license";

        HostelDAO hostelDao = new HostelDAO();


        try {
            int id = Integer.parseInt(request.getParameter("hostelIdup"));
            int status = Integer.parseInt(request.getParameter("status"));

            boolean check = false;
            switch (status) {
                case 0:
                    check = hostelDao.updateHostelStatus(id, -1);
                    break;
                case 1:
                    check = hostelDao.updateHostelStatus(id, 0);
                    break;
                case -1:
                    check = hostelDao.updateHostelStatus(id, 0);
                    break;
            }
            if (check) {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(true)
                        .content("Cập nhật trạng thái khu tro thành công.").build());
            } else {
                request.setAttribute("RESPONSE_MSG", HandlerStatus.builder()
                        .status(false)
                        .content("Đã có lỗi xảy ra! Cập nhật trạng thái khu tro thất bại.").build());
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
