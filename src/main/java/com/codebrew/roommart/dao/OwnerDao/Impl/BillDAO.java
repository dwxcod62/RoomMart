package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IBillDAO;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;
import com.codebrew.roommart.dto.Payment;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BillDAO implements IBillDAO {
    @Override
    public Bill getLastBill(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Bill bill = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT TOP 1 bill_id, total_money, created_date, bill_title, expired_payment_date, payment_date, status, Bill.payment_id as 'payment_id'\n" +
                        "FROM Bill, Payment\n" +
                        "WHERE room_id = ?\n" +
                        "ORDER BY created_date DESC";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int billID = rs.getInt("bill_id");
                    int totalMoney = rs.getInt("total_money");
                    String createdDate = rs.getString("created_date");
                    String billTitle = rs.getString("bill_title");
                    String expiredPaymentDate = rs.getString("expired_payment_date");
                    String paymentDate = rs.getString("payment_date");
                    int status = rs.getInt("status");
                    if (rs.getString("payment_id") == null) {
                        bill = new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(0, null));
                    } else {
                        int paymentID = rs.getInt("payment_id");
                        String paymentName = getPaymentName(paymentID);
                        bill = new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(paymentID, paymentName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return bill;
    }

    @Override
    public BillDetail getBillDetail(int billID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        BillDetail billDetail = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT bill_detail_id, consumeIDStart, consumeIDEnd, accountHostelOwnerID, accountRenterID \n" +
                        "FROM BillDetail WHERE bill_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, billID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int billDetailID = rs.getInt("bill_detail_id");
                    int consumeIDStart = rs.getInt("consumeIDStart");
                    int consumeIDEnd = rs.getInt("consumeIDEnd");
                    int accountHostelOwnerID = rs.getInt("accountHostelOwnerID");
                    int accountRenterID = rs.getInt("accountRenterID");
                    billDetail = new BillDetail(billDetailID, consumeIDStart, consumeIDEnd, accountHostelOwnerID, accountRenterID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return billDetail;
    }

    @Override
    public String getPaymentName(int paymentID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String paymentName = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT payment_name FROM Payment WHERE payment_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, paymentID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    paymentName = rs.getString("payment_name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return paymentName;
    }
}
