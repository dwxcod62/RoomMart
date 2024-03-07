package com.codebrew.roommart.servlets.Payment;

import com.codebrew.roommart.dao.AccountDao;
import com.codebrew.roommart.dao.BillDao;
import com.codebrew.roommart.dao.OwnerDao.Impl.HostelDAO;
import com.codebrew.roommart.dto.HandlerStatus;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.utils.ConfigUtils;
import com.codebrew.roommart.utils.EmailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
@WebServlet(name = "VNPayIPNServlet", value = "/VNPayIPNServlet")
public class VNPayIPNServlet extends HttpServlet {
    private final String SUCCESS = "RenterPayment";
    private final String FAIL = "RenterPayment";
    private final String ERROR = "error";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = -1;
        long amount = -1;
        String url = ERROR;

        String vnp_payDate = null;
        Date payDate = null;
        SimpleDateFormat formatter = null;
        String transactionStatus = null;
        BillDao billDAO = new BillDao();
        HandlerStatus handlerStatus = null;

        try {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            if (fields != null) {
                url = FAIL;
                String vnp_SecureHash = request.getParameter("vnp_SecureHash");
                if (fields.containsKey("vnp_SecureHashType")) {
                    fields.remove("vnp_SecureHashType");
                }
                if (fields.containsKey("vnp_SecureHash")) {
                    fields.remove("vnp_SecureHash");
                }
                String signValue = ConfigUtils.hashAllFields(fields);
                billId = Integer.parseInt(request.getParameter("vnp_TxnRef"));
                amount = Long.parseLong(request.getParameter("vnp_Amount"));

                vnp_payDate = request.getParameter("vnp_PayDate");
                formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                payDate = formatter.parse(vnp_payDate);
                formatter.applyPattern("yyyy/MM/dd HH:mm:ss");

                if (signValue.equals(vnp_SecureHash)) {

                    Bill bill = billDAO.getRenterBillByID(billId);
                    boolean checkOrderId = bill != null; // vnp_TxnRef đơn hàng có tồn tại trong database merchant
                    boolean checkAmount = ((long)bill.getTotalMoney() * 100) == amount; // vnp_Amount is valid  (so sánh số tiền VNPAY request và sô tiền của giao dịch trong database merchant)
                    boolean checkOrderStatus = bill.getStatus() == 0; // PaymnentStatus = 0 (pending)
                    request.setAttribute("billID", billId);
                    if (checkOrderId) {
                        if (checkAmount) {
                            if (checkOrderStatus) {
                                if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                                    //Xu ly thanh toan thanh cong
                                    billDAO.updateBillStatus(bill.getBillID(), 1, formatter.format(payDate), 1);
                                    int ownerId = billDAO.getBillDetail(billId).getAccountHostelOwnerID();
                                    String ownerEmail = new AccountDao().getAccountInformationById(ownerId).getInformation().getEmail();
                                    if (ownerEmail != null) {
                                        Hostel hostel = new HostelDAO().getHostelByRoomId(bill.getRoomID());
                                        String domain = "http://localhost:8080/RoomMart/getRoomInvoiceDetail?billID="+billId+"&hostelID="+hostel.getHostelID()+"&roomID="+ bill.getRoomID();
                                        new EmailUtils().SendMailConfirmPayment(ownerEmail, bill.getRoomID(), hostel.getHostelName() ,bill.getBillTitle(), domain);
                                        handlerStatus = HandlerStatus.builder().status(true).content("Giao dịch thành công!").build();
                                        request.setAttribute("HOSTEL_OWNER_ID", ownerId);
                                        request.setAttribute("SOCKET_MSG", "Hóa đơn #B" + bill.getBillID() + " vừa được thanh toán thành công bằng VNPay!");
                                    }
                                    url = SUCCESS;
                                } else {
                                    handlerStatus = HandlerStatus.builder().status(false).content("Giao dịch không thành công!").build();
                                }
                            } else {
                                handlerStatus = HandlerStatus.builder().status(false).content("Hóa đơn đã được thanh toán!").build();
                            }
                        } else {
                            handlerStatus = HandlerStatus.builder().status(false).content("Invalid Amount").build();
                        }
                    } else {
                        handlerStatus = HandlerStatus.builder().status(true).content("Order not Found").build();
                    }
                }else {
                    handlerStatus = HandlerStatus.builder().status(true).content("Invalid checksum").build();
                }

                request.setAttribute("RESPONSE_MSG", handlerStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ERROR.equalsIgnoreCase(url)) response.sendRedirect(url);
            else request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}