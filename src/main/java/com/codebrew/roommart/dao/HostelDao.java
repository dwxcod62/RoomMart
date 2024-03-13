package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Account;
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
        String sql = "SELECT A.[account_id] AS account_id\n" +
                "FROM [dbo].[Accounts] AS A JOIN [dbo].[Rooms] AS R ON A.[room_id] = R.[room_id]\n" +
                "WHERE R.[hostel_id] = ? AND A.[status] = 1";
//        System.out.println(sql);
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
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

    public Hostel getHostelById(int hostelId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("select * from [dbo].[Hostels] where hostel_id = ?");
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
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

    public int getHostelByRoomId(int room_id) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int hostel_id = -1;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("select hostel_id from [Rooms] where room_id = ?");
                pst.setInt(1, room_id);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    hostel_id = rs.getInt("hostel_id");
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
        return hostel_id;
    }


    private static final String GET_HOSTEL_BY_OWNER_ID =
            "SELECT hostel_id, owner_account_id, name, address, ward, district, city FROM [dbo].[Hostels] WHERE owner_account_id = ?";
    public List<Hostel> getHostelByOwnerId(int hostelOwnerAccountID) throws SQLException {
        List<Hostel> listHostels = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_BY_OWNER_ID);
                pst.setInt(1, hostelOwnerAccountID);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int hostelID = rs.getInt("hostel_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = Hostel.builder()
                            .hostelID(hostelID)
                            .hostelOwnerAccountID(hostelOwnerAccountID)
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city).build();
                    listHostels.add(hostel);
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
        return listHostels;
    }


}
