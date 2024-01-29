package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Invoice;
import com.codebrew.roommart.dto.Payment;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceDAO {
    private static final String GET_INVOICE_BY_RENTER_ID = "SELECT Bill.bill_id, Bill.total_money, Bill.created_date, Bill.bill_title, \n" +
            "Bill.expired_payment_date, Bill.payment_date, Bill.status, Bill.payment_id, Bill.room_id\n" +
            "FROM Accounts INNER JOIN Contracts ON Accounts.account_id=Contracts.renter_id\n" +
            "INNER JOIN Rooms ON Contracts.room_id=Rooms.room_id\n" +
            "INNER JOIN Bill ON Contracts.room_id=Bill.room_id\n" +
            "WHERE Accounts.account_id = ?\n" +
            "ORDER BY Bill.created_date DESC";
    public List<Invoice> getInvoiceListByRenterID(int renterID) throws SQLException {
        List<Invoice> invoiceList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cn = DatabaseConnector.makeConnection();
            ptm = cn.prepareStatement(GET_INVOICE_BY_RENTER_ID);
            ptm.setInt(1, renterID);
            rs = ptm.executeQuery();
            while (rs != null && rs.next()) {
                int invoiceID = rs.getInt("bill_id");
                int totalMoney = rs.getInt("total_money");
                String createdDate = dateFormat.format(rs.getDate("created_date"));
                String invoiceTitle = rs.getString("bill_title");
                String expiredPaymentDate = dateFormat.format(rs.getDate("expired_payment_date"));
                Date paymentDateTemp = rs.getDate("payment_date");
                String paymentDate;
                if (paymentDateTemp == null || paymentDateTemp.equals("")) {
                    paymentDate = "";
                } else {
                    paymentDate = dateFormat.format(paymentDateTemp);
                }
                boolean status = rs.getBoolean("status");
                int paymentID = rs.getInt("payment_id");
                int roomID = rs.getInt("room_id");
                invoiceList.add(Invoice.builder()
                        .invoiceID(invoiceID)
                        .createdDate(createdDate)
                        .totalMoney(totalMoney)
                        .invoiceTitle(invoiceTitle)
                        .expiredPaymentDate(expiredPaymentDate)
                        .paymentDate(paymentDate)
                        .status(status)
                        .payment(Payment.builder().paymentID(paymentID).build())
                        .roomID(roomID)
                        .build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return invoiceList;
    }
}
