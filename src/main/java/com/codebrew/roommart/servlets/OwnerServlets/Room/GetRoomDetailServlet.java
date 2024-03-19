package com.codebrew.roommart.servlets.OwnerServlets.Room;


import com.codebrew.roommart.dao.OwnerDao.IAccountDAO;
import com.codebrew.roommart.dao.OwnerDao.IConsumeDAO;
import com.codebrew.roommart.dao.OwnerDao.IInfrastructureDAO;
import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dao.OwnerDao.Impl.*;
import com.codebrew.roommart.dao.RoommateInfoDao;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;
import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.utils.Decorations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetRoomDetailServlet", value = "/ownerRoomDetail")
public class GetRoomDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Decorations.measureExecutionTime(() -> {
            try {
                get(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        }, "ownerRoomDetail.doGet");
    }

    private void get(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String ERROR = "error";
        String url = "pages/owner/room/room-detail.jsp";

        try {
            HttpSession session = request.getSession();

            int accID = ((Account) session.getAttribute("USER")).getAccId();

            int hostelID = (request.getParameter("hostelID") != null) ? Integer.parseInt(request.getParameter("hostelID")) : ((Hostel) session.getAttribute("hostel")).getHostelID();
//                int hostelID =  Integer.parseInt(request.getParameter("hostelID"));

            int roomId = (request.getParameter("roomID") != null) ? Integer.parseInt(request.getParameter("roomID")) : (int) session.getAttribute("current_room_id");
//                int roomId = Integer.parseInt(request.getParameter("roomID"));

            System.out.print(" START CHECK ROOM OF OWNER: ");
            if (new HostelDAO().checkOwnerRoom(accID, roomId)) {
                System.out.println("SUCCESS");

                IRoomDAO roomDAO = new RoomDAO();
                IConsumeDAO consumeDAO = new ConsumeDAO();
                IAccountDAO accountDAO = new AccountDAO();
                IInfrastructureDAO infrastructureDAO = new InfrastructureDAO();

                System.out.print(" START GET ROOM INFORMATION BY ROOM ID ( " + roomId + " ) : ");
                Room room = roomDAO.getRoomInformationByRoomId(roomId, hostelID, accID); // lấy thông tin phòng theo roomId và hostelId
                if (room != null) {
                    System.out.println(" SUCCESS");
                    session.setAttribute("room", room);
                    session.setAttribute("current_room_id", room.getRoomId());

                    com.codebrew.roommart.dto.OwnerDTO.Hostel hostel = new HostelDAO().getHostelById(hostelID);

                    session.setAttribute("hostel", hostel);
                    request.setAttribute("hid",hostelID);


                    Contract contract = new ContractDAO().getContract(roomId);
                    request.setAttribute("contractRoom", contract);

                    List<Consume> consumeList = consumeDAO.getConsumeHistory(roomId); // lịch sử tiêu thụ
                    request.setAttribute("consumeList", consumeList);

                    Consume consumeNumber = consumeDAO.getNearestConsume(roomId); // điện nước hiện tại
                    request.setAttribute("consumeNumber", consumeNumber);

                    List<InfrastructureItem> infrastructureItemList = infrastructureDAO.getAllInfrastructure(); // cơ sở vật chất
                    request.setAttribute("infrastructureList", infrastructureItemList);


                    List<Consume> consumeThisMonth = consumeDAO.getConsumeThisMonth(roomId); // điện nước tiêu thụ, lấy status = 0, để thanh toán hóa đơn(set status)
                    request.setAttribute("consumeListThisMonth", consumeThisMonth);

                    System.out.print(" START GET CONTRACT BY ROOM ID ( " + roomId + " ) : ");
                    if (contract != null) {
                        System.out.println(" SUCCESS");
                        Account renterAccount = accountDAO.getAccountById(contract.getRenterId());
                        request.setAttribute("renterAccount", renterAccount);

                        List<RoommateInfo> listRoommatesInfo = new RoommateInfoDao().getListRoommatesOfAnAccount(contract.getRenterId()); // lấy thông tin bạn cùng phòng
                        request.setAttribute("listRoommatesInfo", listRoommatesInfo);
                    }

                    Bill bill = new BillDAO().getLastBill(roomId); // lấy bill gần nhất ( theo ngày tạo ) (select top1)
                    request.setAttribute("billRoom", bill);

                    List<Payment> payments = new PaymentDAO().getPaymentList(); // phương thức thanh toán
                    request.setAttribute("paymentList", payments);

                    if (bill != null) {
                        int billID = bill.getBillID();
                        BillDetail billDetail = new BillDAO().getBillDetail(billID); // lấy billDetail (chi tiết hóa đơn)
                        int consumeIDStart = billDetail.getConsumeIDStart();
                        int consumeIDEnd = billDetail.getConsumeIDEnd();

                        if (bill.getStatus() == 0 || bill.getPayment() != null) {
                            String paymentName = new BillDAO().getPaymentName(bill.getPayment().getPaymentID()); // lấy tên của hình thức thanh toán
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
                        System.out.println(billDetail.toString());
                        AccountInfo accountHOInfo = accountDAO.getAccountInformationById(accountHOID);
                        AccountInfo accountRenterInfo = accountDAO.getAccountInformationById(accountRenterID);
                        System.out.println(accountHOID + ", " + accountRenterID);

                        request.setAttribute("billMakerFullName", accountHOInfo.getInformation().getFullname());
                        request.setAttribute("billPaymenterFullName", accountRenterInfo.getInformation().getFullname());
                    }
                    List<Infrastructures> infrastructures = infrastructureDAO.getRoomInfrastructures(roomId);
                    request.setAttribute("infrastructures", infrastructures);
                } else {
                    System.out.println(" FAIL");
                    url = ERROR;
                }
            } else {
                System.out.println(" FAIL");
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
