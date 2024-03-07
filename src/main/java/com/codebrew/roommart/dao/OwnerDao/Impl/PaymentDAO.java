package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IPaymentDAO;
import com.codebrew.roommart.dto.Payment;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements IPaymentDAO {
    @Override
    public boolean updateBillStatus(int billID, int paymentID) {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean updated = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                String sql = "UPDATE Bill\n" +
                        "SET payment_date = GETDATE(), status = 1, payment_id = ?\n" +
                        "WHERE bill_id = ?\n";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, paymentID);
                pst.setInt(2, billID);

                if (pst.executeUpdate() > 0) {
                    updated = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, null);
        }
        return updated;
    }

    @Override
    public List<Payment> getPaymentList() {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Payment> paymentList = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();

            if (cn != null) {
                String sql = "SELECT payment_id, payment_name FROM Payment";
                pst = cn.prepareStatement(sql);
                rs = pst.executeQuery();

                if (rs != null) {
                    while (rs.next()) {
                        int paymentId = rs.getInt("payment_id");
                        String paymentName = rs.getString("payment_name");
                        paymentList.add(new Payment(paymentId, paymentName));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return paymentList;
    }
}
