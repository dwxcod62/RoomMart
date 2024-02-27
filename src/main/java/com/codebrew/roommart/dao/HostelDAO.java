package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public ArrayList<Integer> getListRenterIdByHostelId(int hostelId){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Integer> accIdList = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("SELECT A.[account_id] AS account_id\n" +
                        "FROM [dbo].[Accounts] AS A JOIN [dbo].[Rooms] AS R ON A.[room_id] = R.[room_id]\n" +
                        "WHERE R.[hostel_id] = ? AND A.[status] = 1");
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int accId = rs.getInt("account_id");
                    accIdList.add(accId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return accIdList;
    }
}
