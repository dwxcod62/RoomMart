package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HostelDAO {
    private static final String
            GET_HOSTEL_BY_RENTER_ID = "SELECT h.name, h.address, h.ward, h.district, h.city " +
            "FROM contract_main AS cm " +
            "INNER JOIN rooms AS r ON cm.room_id = r.room_id " +
            "INNER JOIN hostels AS h ON r.hostel_id = h.hostel_id " +
            "WHERE cm.renter_id = ?";


    public Hostel getHostelByRenterId(int renterId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_BY_RENTER_ID);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = Hostel.builder()
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
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
        return hostel;
    }

}
