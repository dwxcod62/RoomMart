package com.codebrew.roommart.servlets.OwnerServlet.Room;

import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.Status;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ViewContractCreate", value = "/ViewContractCreate")
public class ViewContractCreate extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                goToCreateContract(request, response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ViewContractCreate.doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void goToCreateContract(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String url = "error";
        Room room = (Room) session.getAttribute("room");

        if (room != null){
            System.out.println("- Lay ID thanh cong ");
            url = "create-contract-page";
        } else {
            System.out.println("- ID sai ");
            url = "owner-get-room-list";
            Status status = Status.builder()
                    .status(false)
                    .content("Something wrong, try again!").build();

            request.setAttribute("RESPONSE_MSG", status);
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

}