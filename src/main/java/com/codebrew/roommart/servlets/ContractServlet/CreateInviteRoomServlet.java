package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.HostelOwnerDao;
import com.codebrew.roommart.dao.InformationDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.Room;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateInviteRoomServlet", value = "/CreateInviteRoomServlet")
public class CreateInviteRoomServlet extends HttpServlet {
    private final String ERROR = "error";
    private final String FAIL = "CreateContractPage";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }

    private void create(HttpServletRequest req, HttpServletResponse res) throws Exception{
        req.setCharacterEncoding("UTF-8");

        AccountDao accountDAO = new AccountDao();
        InformationDao informationDao = new InformationDao();
        HandlerStatus handlerStatus = null;
        Account owner;
        String url = ERROR;;
        int ownerId;
        int roomID;

        try {
            HttpSession session = req.getSession(false);

            if (session != null) {
                roomID = (int) session.getAttribute("current_room_id");
                owner = (Account) session.getAttribute("USER");

                if (owner != null && roomID > 0) {
                    ownerId = owner.getAccId();
                    if (new HostelOwnerDao().checkOwnerRoom(ownerId, roomID)) {
                        url = FAIL;

                        Room room = new RoomDAO().getRoomById(roomID);
                        if (room.getRoomStatus() == 1) {
                            String email = req.getParameter("room-email");
                            String price = req.getParameter("room-fee");
                            String deposit = req.getParameter("room-deposit");
                            String startDate = req.getParameter("room-startdate");
                            String endDate = req.getParameter("room-enddate");
                            int roomElectric = Integer.parseInt(req.getParameter("room-electric"));
                            int roomWater = Integer.parseInt(req.getParameter("room-water"));

                            if (informationDao.isExistEmail(email)) {


                            } else {
                                handlerStatus = HandlerStatus.builder().
                                        status(false).
                                        content("Email không tồn tại hoặc đã được tạo hợp đồng! Vui lòng chọn email khác!").build();

                                req.setAttribute("email", email);
                                req.setAttribute("price", price);
                                req.setAttribute("deposit", deposit);
                                req.setAttribute("startDate", startDate);
                                req.setAttribute("endDate", endDate);
                                req.setAttribute("roomElectric", roomElectric);
                                req.setAttribute("roomWater", roomWater);
                                req.setAttribute("errorType", "username");
                            }
                        } else {
                            handlerStatus = HandlerStatus.builder().
                                    status(false).
                                    content("Phòng này đã và đang có người thuê! Không thể tạo hợp đồng mới cho phòng!").build();
                            url = FAIL;
                        }
                    }
                }
                req.setAttribute("RESPONSE_MSG", handlerStatus);
            } else {

            }


        } catch ( Exception e){
            System.out.println(e.toString());
        }

    }

}