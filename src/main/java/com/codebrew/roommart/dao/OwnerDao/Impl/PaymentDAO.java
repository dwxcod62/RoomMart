package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IPaymentDAO;
import com.codebrew.roommart.dto.Payment;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO implements IPaymentDAO {
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
