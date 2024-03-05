package com.codebrew.roommart.servlets.ContractServlet;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dao.OwnerDao.Impl.RoomDAO;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.Decorations;
import com.codebrew.roommart.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CreateContractServlet", value = "/CreateContractServlet")
public class CreateContractServlet extends HttpServlet {

    private final String ERROR = "error";
    private final String FAIL = "CreateContractPage";
    private final String SUCCESS = "ConfirmContract";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                create(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "CreateContractServlet.doPost");
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
        int hostelID;

        try {
            HttpSession session = req.getSession(false);

            if (session != null) {
                roomID = Integer.parseInt(req.getParameter("room_id"));
                hostelID = Integer.parseInt(req.getParameter("hostel_id"));
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
                                url = SUCCESS;

                                Information _renter_info = new InformationDao().getAccountInformationByEmail(email);
                                _renter_info.setPhone(new StringUtils().maskCccd(_renter_info.getPhone()));
                                _renter_info.setCccd(new StringUtils().maskCccd(_renter_info.getCccd()));

                                Hostel _hostel = new HostelDao().getHostelById(hostelID);
                                session.setAttribute("CONTRACT_HOSTEL", _hostel);

                                Room _room = new RoomDAO().getRoomById(roomID);
                                session.setAttribute("CONTRACT_ROOM", _room);

                                Contract _contract = new Contract().builder()
                                                    .startDate(startDate)
                                                    .room_id(roomID)
                                                    .hostelOwnerId(hostelID)
                                                    .renterId(_renter_info.getAccount_id())
                                                    .status(1)
                                                    .price(Integer.parseInt(price))
                                                    .deposit(Integer.parseInt(deposit))
                                                    .build();

                                List<Infrastructures> _list_Infrastructures = new InfrastructureDao().getRoomInfrastructures(roomID);
                                List<ServiceInfo> _list_Services = new ServiceInfoDAO().getServicesOfHostel(hostelID);

                                session.setAttribute("CONTRACT_ROOM_INFRASTRUCTURE_LIST", _list_Infrastructures);
                                session.setAttribute("CONTRACT_SERVICES_LIST", _list_Services);
                                session.setAttribute("CONTRACT_OWNER", owner.getAccountInfo());
                                session.setAttribute("CONTRACT_RENTER", _renter_info);
                                session.setAttribute("CONTRACT", _contract);

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
            }
        } catch ( Exception e){
            System.out.println(e.toString());
        } finally {
            if (ERROR.equalsIgnoreCase(url)) res.sendRedirect(url);
            else req.getRequestDispatcher(url).forward(req, res);
        }

    }
}