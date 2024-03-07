package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.ContractDao;
import com.codebrew.roommart.dao.RoomDao;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteContractServlet", value = "/DeleteContractServlet")
public class DeleteContractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("denied");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                cancelContract(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "DeleteContractServlet.doPost");
    }

    private void cancelContract(HttpServletRequest req, HttpServletResponse res) throws Exception{
        String url = "error";
        HttpSession session = req.getSession();
        Room r = (Room) session.getAttribute("room");

        try{
            RoomDao roomDao = new RoomDao();
            ContractDao contractDao = new ContractDao();

            roomDao.updateRoomStatus(r.getRoomId(), 1);
            url = "ownerRoomDetail?" + "roomID=" + r.getRoomId() + "&hostelID=" + r.getHostelId();
        } catch ( Exception e){
            System.out.println(e);
        } finally {
            res.sendRedirect(url);
        }
    }
}