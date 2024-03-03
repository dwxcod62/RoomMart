package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HostelDao {

    private static final String GET_ALL_HOSTEL = "select * from Hostels";
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
    public List<Hostel> getAllHostel() throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Hostel> hostel = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_ALL_HOSTEL);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int hostelId = rs.getInt("hostel_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    int hostelOwnerAccountID = rs.getInt("owner_account_id");
                    hostel.add(new Hostel(hostelId, hostelOwnerAccountID, name, address, ward, district, city));
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
