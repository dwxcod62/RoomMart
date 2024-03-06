package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HostelOwnerDao {

    private static final String GET_OWNERID_BY_HOSTELID =
            "SELECT owner_account_id" +
                    " FROM hostels "+
                    "WHERE hostel_id = ?";
    public int getOwnerIdByHostelId(int hostel_id) throws SQLException {
        System.out.println("getOwnerIdByHostelId");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;
        int hostelOwnerAccountID=0;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_OWNERID_BY_HOSTELID);
                pst.setInt(1, hostel_id);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {

                    hostelOwnerAccountID = rs.getInt("owner_account_id");

                }
            }
        } catch (Exception e) {
            System.out.println("getOwnerIdByHostelId - owner servlet ERROR");
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return hostelOwnerAccountID;
    }

    public boolean checkOwnerRoom(int accId, int roomId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT R.room_id FROM Rooms AS R JOIN Hostels AS H ON R.hostel_id = H.hostel_id\n" +
                        "WHERE R.room_id = ? AND H.owner_account_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomId);
                pst.setInt(2, accId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return result;
    }
}
