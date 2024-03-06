package com.codebrew.roommart.servlets.RenterServlet;

import com.codebrew.roommart.dao.*;
import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;
import com.codebrew.roommart.dto.OwnerDTO.Consume;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "GetRenterBillPaymentServlet", value = "/GetRenterBillPaymentServlet")
public class GetRenterBillPaymentServlet extends HttpServlet {
    private static final String SUCCESS = "renter-Payment";
    private static final String ERROR = "renter-Payment";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            request.setAttribute("uri", "/RoomMart/GetRenterBillServlet");
            Account account = (Account) session.getAttribute("USER");
            int accID = account.getAccId();

            BillDao billDAO = new BillDao();
            int billID = (request.getAttribute("billID") != null) ? (int) request.getAttribute("billID") : Integer.parseInt(request.getParameter("billID")) ;
            BillDetail billDetail = new BillDao().getBillDetail(billID);

            ContractDao roomDAO = new ContractDao();
            Room room = roomDAO.getRoomByContract(account.getAccId());
            request.setAttribute("RoomInfor", room);

            Bill bill = billDAO.getRenterBillByID(billID);

            if (bill != null){
                request.setAttribute("BILL", bill);
                System.out.println(bill);

                //get number electric and water
                int consumeIDStart = billDetail.getConsumeIDStart();
                int consumeIDEnd = billDetail.getConsumeIDEnd();
                Consume consumeStart = new ConsumeDao().getConsumeByID(consumeIDStart);
                Consume consumeEnd = new ConsumeDao().getConsumeByID(consumeIDEnd);
                request.setAttribute("CONSUME_START", consumeStart);
                request.setAttribute("CONSUME_END", consumeEnd);
                System.out.println(consumeStart);
                System.out.println(consumeEnd);

                url = SUCCESS;
            }

            //Get service
            HostelDao hostelDAO = new HostelDao();
            Hostel hostel = hostelDAO.getHostelByRenterId(accID);
            int hostelID = hostel.getHostelID();
            int billDetailID = billDetail.getBillDetailID();
            ServiceInfoDAO serviceInfoDao = new ServiceInfoDAO();
            List<ServiceInfo> serviceInfoList = serviceInfoDao.getServiceOfBill(billDetailID, hostelID);
            if (serviceInfoList.size() > 0) {
                request.setAttribute("LIST_SERVICES", serviceInfoList);
                url = SUCCESS;
            }
        }catch (Exception e){
            log("Error at GetRenterBillServlet: " + e.toString());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}