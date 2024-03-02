package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Bill;
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

public class BillDao {
    private static final String GET_BILL_BY_RENTER_ID =
            "SELECT b.bill_id, b.total_money, b.created_date, b.bill_title, " +
                "b.expired_payment_date, b.payment_date, b.status, b.payment_id, b.room_id\n" +
                "FROM Bill b\n" +
                "INNER JOIN Contracts c ON b.room_id = c.room_id\n" +
                "WHERE c.renter_id = ?";
    public List<Bill> getBllListByRenterID(int renterID) throws SQLException {
        List<Bill> billList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cn = DatabaseConnector.makeConnection();
            ptm = cn.prepareStatement(GET_BILL_BY_RENTER_ID);
            ptm.setInt(1, renterID);
            rs = ptm.executeQuery();
            while (rs != null && rs.next()) {
                int billID = rs.getInt("bill_id");
                int totalMoney = rs.getInt("total_money");
                String createdDate = dateFormat.format(rs.getDate("created_date"));
                String billTitle = rs.getString("bill_title");
                String expiredPaymentDate = dateFormat.format(rs.getDate("expired_payment_date"));
                Date paymentDateTemp = rs.getDate("payment_date");
                String paymentDate;
                if (paymentDateTemp == null || paymentDateTemp.equals("")) {
                    paymentDate = "";
                } else {
                    paymentDate = dateFormat.format(paymentDateTemp);
                }
                int status = rs.getInt("status");
                int paymentID = rs.getInt("payment_id");
                int roomID = rs.getInt("room_id");
                billList.add(Bill.builder()
                        .billID(billID)
                        .createdDate(createdDate)
                        .totalMoney(totalMoney)
                        .billTitle(billTitle)
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
        return billList;
    }
}
