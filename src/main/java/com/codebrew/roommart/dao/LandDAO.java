package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Land;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LandDAO {
    private static final String GET_LAND_BY_RENTER_ID =
            "SELECT Hostels.hostel_id, Hostels.owner_account_id, Hostels.name, Hostels.address," +
                    "Hostels.ward, Hostels.district, Hostels.city\n" +
                    "FROM public.Hostels\n" +
                    "INNER JOIN public.Rooms ON Hostels.hostel_id = Rooms.hostel_id\n" +
                    "INNER JOIN public.Contracts ON Rooms.room_id = Contracts.room_id\n" +
                    "INNER JOIN public.Accounts ON Contracts.renter_id = Accounts.account_id\n" +
                    "WHERE account_id = ?";
    public Land getLandByRenterId(int renterId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Land land = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_LAND_BY_RENTER_ID);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int landId = rs.getInt("hostel_id");
                    int landOwnerAccountID = rs.getInt("owner_account_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    land = new Land(landId, landOwnerAccountID, name, address, ward, district, city);
                }
            }
        } catch (Exception e) {
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
        return land;
    }
}
