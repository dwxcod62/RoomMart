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

    private static final String GET_HOSTEL_BY_RENTER_ID =
            "SELECT Hostels.hostel_id, Hostels.owner_account_id, Hostels.name, " +
            "Hostels.address, Hostels.ward, Hostels.district, Hostels.city\n" +
            "FROM Hostels\n" +
            "INNER JOIN Rooms ON Hostels.hostel_id = Rooms.hostel_id\n" +
            "INNER JOIN Contracts ON Rooms.room_id = Contracts.room_id\n" +
            "INNER JOIN Accounts ON Contracts.renter_id = Accounts.account_id\n" +
            "WHERE Accounts.account_id = ?";


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
                    int hostelId = rs.getInt("hostel_id");
                    int hostelOwnerAccountID = rs.getInt("owner_account_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = new Hostel(hostelId, hostelOwnerAccountID, name, address, ward, district, city);
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
