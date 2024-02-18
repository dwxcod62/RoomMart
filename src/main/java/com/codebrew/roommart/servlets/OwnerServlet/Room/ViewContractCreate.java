package com.codebrew.roommart.servlets.OwnerServlet.Room;

import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.Status;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewContractCreate", value = "/ViewContractCreate")
public class ViewContractCreate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("USER");
        RoomDAO dao = new RoomDAO();

        int room_id = Integer.parseInt(request.getParameter("roomID"));
        String url = "error";

        Room room = dao.checkRoomOwner(acc.getAccId(), room_id);
        if (room != null){
            url = "create-contract-page";
            request.setAttribute("ROOM", room);

        } else {
            url = "owner-get-room-list";
            Status status = Status.builder()
                    .status(false)
                    .content("Something wrong, try again!").build();

            request.setAttribute("RESPONSE_MSG", status);
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}