package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;

public class ConsumeDao {
    public Boolean updateConsumeNumber(Consume consume) {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean isSuccess = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String sql = "INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id)\n" +
                        "VALUES (?, ?, CURRENT_DATE , ?, ?)";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, consume.getNumberElectric());
                pst.setInt(2, consume.getNumberWater());
                pst.setInt(3, consume.getStatus());
                pst.setInt(4, consume.getRoomID());

                if (pst.executeUpdate() > 0) {
                    isSuccess = true;
                } else {
                    cn.rollback();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return isSuccess;
    }

}
