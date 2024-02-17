package com.codebrew.roommart.servlets.OwnerServlet.Room;


import com.codebrew.roommart.dao.OwnerDao.IAccountDAO;
import com.codebrew.roommart.dao.OwnerDao.IConsumeDAO;
import com.codebrew.roommart.dao.OwnerDao.IInfrastructureDAO;
import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.*;
import com.codebrew.roommart.dao.RoommateInfoDAO;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;
import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "GetRoomDetailServlet", value = "/ownerRoomDetail")
public class GetRoomDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String ERROR = "error-page";
            String url = "pages/owner/room/room-detail.jsp";

            try {
                HttpSession session = request.getSession();

                int accID = ((Account) session.getAttribute("USER")).getAccId();

                int hostelID = (request.getParameter("hostelID") != null ) ? Integer.parseInt(request.getParameter("hostelID")) : ((Hostel) session.getAttribute("hostel")).getHostelID();
//                int hostelID =  Integer.parseInt(request.getParameter("hostelID"));

                int roomId = (request.getParameter("roomID") != null ) ? Integer.parseInt(request.getParameter("roomID")) : (int) session.getAttribute("current_room_id");
//                int roomId = Integer.parseInt(request.getParameter("roomID"));



                // Check xem roomID có thuộc ownerID không
                if (new HostelDAO().checkOwnerRoom(accID, roomId)) {

                    IRoomDAO roomDAO = new RoomDAO();
                    IConsumeDAO consumeDAO = new ConsumeDAO();
                    IAccountDAO accountDAO = new AccountDAO();
                    IInfrastructureDAO infrastructureDAO = new InfrastructureDAO();

                    Room room = roomDAO.getRoomInformationByRoomId(roomId, hostelID, accID);
                    if(room != null){
                        session.setAttribute("room", room);
                        session.setAttribute("current_room_id", room.getRoomId());

                        Hostel hostel = new HostelDAO().getHostelById(hostelID);
                        session.setAttribute("hostel", hostel);

                        Contract contract = new ContractDAO().getContract(roomId);
                        request.setAttribute("contractRoom", contract);

                        List<Consume> consumeList = consumeDAO.getConsumeHistory(roomId);
                        request.setAttribute("consumeList", consumeList);

                        Consume consumeNumber = consumeDAO.getNearestConsume(roomId);
                        request.setAttribute("consumeNumber", consumeNumber);

                        List<InfrastructureItem> infrastructureItemList = infrastructureDAO.getAllInfrastructure();
                        request.setAttribute("infrastructureList", infrastructureItemList);

                        Bill bill = new BillDAO().getLastBill(roomId);
                        request.setAttribute("billRoom", bill);

                        List<Consume> consumeThisMonth = new ConsumeDAO().getConsumeThisMonth(roomId);
                        request.setAttribute("consumeListThisMonth", consumeThisMonth);

                        if (contract != null) {
                            Account renterAccount = accountDAO.getAccountById(contract.getRenterId());
                            request.setAttribute("renterAccount", renterAccount);

                            List<Roommate> listRoommatesInfo = new RoommateInfoDAO().getListRoommatesOfAnAccount(contract.getRenterId()); // loi khong the convert to int
                            request.setAttribute("listRoommatesInfo", null);
                        }

                        List<Payment> payments = new PaymentDAO().getPaymentList();
                        request.setAttribute("paymentList", payments);

                        if (bill != null) {
                            int billID = bill.getBillID();
                            BillDetail billDetail = new BillDAO().getBillDetail(billID);
                            int consumeIDStart = billDetail.getConsumeIDStart();
                            int consumeIDEnd = billDetail.getConsumeIDEnd();

                            if (bill.getStatus() == 0 || bill.getPayment() != null) {
                                String paymentName = new BillDAO().getPaymentName(bill.getPayment().getPaymentID());
                                request.setAttribute("paymentName", paymentName);
                            }

                            Consume consumeStart = new ConsumeDAO().getConsumeByID(consumeIDStart);
                            Consume consumeEnd = new ConsumeDAO().getConsumeByID(consumeIDEnd);

                            int numberConsumeElectric = consumeEnd.getNumberElectric() - consumeStart.getNumberElectric();
                            int numberConsumeWater = consumeEnd.getNumberWater() - consumeStart.getNumberWater();

                            request.setAttribute("consumeStart", consumeStart);
                            request.setAttribute("consumeEnd", consumeEnd);

                            int billDetailID = billDetail.getBillDetailID();
                            List<ServiceInfo> serviceInfos = new ServiceInfoDAO().getServiceOfBill(billDetailID, hostelID);
                            request.setAttribute("serviceInfo", serviceInfos);

                            int accountHOID = billDetail.getAccountHostelOwnerID();
                            int accountRenterID = billDetail.getAccountRenterID();
                            UserInformation accountHOInfo = accountDAO.getAccountInformationById(accountHOID);
                            UserInformation accountRenterInfo = accountDAO.getAccountInformationById(accountRenterID);

                            request.setAttribute("billMakerFullName", accountHOInfo.getFullname());
                            request.setAttribute("billPaymenterFullName", accountRenterInfo.getFullname());
                        }
                        List<Infrastructures> infrastructures = infrastructureDAO.getRoomInfrastructures(roomId);
                        request.setAttribute("infrastructures", infrastructures);
                    }else {
                        url = ERROR;
                    }
                }else {
                    url = ERROR;
                }

                session.setAttribute("CURRENT_PAGE", "room");




            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (ERROR.equalsIgnoreCase(url))
                    response.sendRedirect(url);
                else
                    request.getRequestDispatcher(url).forward(request, response);
            }
//        request.getRequestDispatcher("/pages/owner/room/room-detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
