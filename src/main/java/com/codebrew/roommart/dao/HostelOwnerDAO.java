package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HostelOwnerDAO {
    private static final String GET_OWNERID_BY_HOSTELID =
            "SELECT Hostels.owner_account_id" +
                    "FROM public.Hostels\n"+
                    "WHERE hostel_id = ?";
    public int getOwnerIdByHostelId(int hostel_id) throws SQLException {
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
}
