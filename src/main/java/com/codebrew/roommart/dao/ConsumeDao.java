package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsumeDao {
    private static final String GET_CONSUME_BY_ID =
            "SELECT number_electric, number_water, update_date, status, room_id\n" +
                    "FROM Consumes\n" +
                    "WHERE consume_id = ?";
    public Consume getConsumeByID(int consumeID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Consume consume = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_CONSUME_BY_ID);
                pst.setInt(1, consumeID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int numberElectric = rs.getInt("number_electric");
                    int numberWater = rs.getInt("number_water");
                    String updateConsumeDate = rs.getString("update_date");
                    int status = rs.getInt("status");
                    int roomID = rs.getInt("room_id");
                    consume = Consume.builder()
                            .consumeID(consumeID)
                            .roomID(roomID)
                            .numberElectric(numberElectric)
                            .numberWater(numberWater)
                            .updateDate(updateConsumeDate)
                            .status(status).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
        return consume;
    }
}
