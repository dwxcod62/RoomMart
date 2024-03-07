package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.dto.RoommateInfo;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoommateInfoDao {

    // ------------------------------ Query ------------------------------

    private static final String GET_LIST_ROOMMATES_OF_AN_ACCOUNT =
            "SELECT roomate_info_id, fullname, email, birthday, sex, phone, address, \n" +
            "identity_card_number, parent_name, parent_phone FROM [dbo].[RoomateInformations] \n" +
            "WHERE account_renter_id = ?";
    private static final String GET_LIST_ROOMMATES_BY_RENTER_ID =
            "SELECT ri.roomate_info_id, ri.fullname, ri.email, " +
                    "ri.birthday, ri.sex, ri.phone, ri.address, ri.parent_name, ri.parent_phone\n" +
                    "FROM roomateinformations ri\n" +
                    "JOIN Contracts c ON c.renter_id = ri.account_renter_id\n" +
                    "WHERE room_id IN (SELECT room_id FROM Contracts WHERE renter_id = ?)";


    // ------------------------------ Func ------------------------------

    public List<RoommateInfo> getListRoommatesOfAnAccount(int accountId) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        List<RoommateInfo> list = new ArrayList<RoommateInfo>();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                psm = conn.prepareStatement(GET_LIST_ROOMMATES_OF_AN_ACCOUNT);
                psm.setInt(1, accountId);
                rs = psm.executeQuery();

                while (rs.next()) {
                    Information information = Information.builder()
                            .fullname(rs.getString("fullname"))
                            .email(rs.getString("email"))
                            .birthday(rs.getString("birthday"))
                            .sex(rs.getInt("sex"))
                            .phone(rs.getString("phone"))
                            .address(rs.getString("address"))
                            .cccd(rs.getString("identity_card_number"))
                            .build();
                    RoommateInfo roommateInfo = RoommateInfo.builder()
                            .roommateID(rs.getInt("roomate_info_id"))
                            .information(information)
                            .parentName(rs.getString("parent_name"))
                            .parentPhone(rs.getString("parent_phone")).build();
                    list.add(roommateInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, rs);
        }
        return list;
    }

    public List<RoommateInfo> getListRoommatesByRenterID(int renterId) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        List<RoommateInfo> list = new ArrayList<RoommateInfo>();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                psm = conn.prepareStatement(GET_LIST_ROOMMATES_BY_RENTER_ID);
                psm.setInt(1, renterId);
                rs = psm.executeQuery();

                while (rs.next()) {
                    Information information = Information.builder()
                            .fullname(rs.getString("fullname"))
                            .email(rs.getString("email"))
                            .birthday(rs.getDate("birthday").toString())
                            .sex(rs.getInt("sex"))
                            .phone(rs.getString("phone"))
                            .address(rs.getString("address"))
                            .build();
                    RoommateInfo roommateInfo = RoommateInfo.builder()
                            .roommateID(rs.getInt("roomate_info_id"))
                            .information(information)
                            .parentName(rs.getString("parent_name"))
                            .parentPhone(rs.getString("parent_phone")).build();
                    list.add(roommateInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (psm != null) psm.close();
            if (conn != null) conn.close();
        }
        return list;
    }

    public boolean AddRoommateInformationOfAnAccount(RoommateInfo roommateInfo, int accountId) {
        Connection conn = null;
        PreparedStatement psm = null;
        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                String ADD_ROOMMATE_INFORMATION_OF_AN_ACCOUNT =
                        "INSERT INTO [dbo].[RoomateInformations] (fullname, email, birthday, \n" +
                                "sex, phone, address, identity_card_number, parent_name, parent_phone, account_renter_id) \n" +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                psm = conn.prepareStatement(ADD_ROOMMATE_INFORMATION_OF_AN_ACCOUNT);
                psm.setString(1, roommateInfo.getInformation().getFullname());
                psm.setString(2, roommateInfo.getInformation().getEmail());
                psm.setString(3, roommateInfo.getInformation().getBirthday());
                psm.setInt(4, roommateInfo.getInformation().getSex());
                psm.setString(5, roommateInfo.getInformation().getPhone());
                psm.setString(6, roommateInfo.getInformation().getAddress());
                psm.setString(7, roommateInfo.getInformation().getCccd());
                psm.setString(8, roommateInfo.getParentName());
                psm.setString(9, roommateInfo.getParentPhone());
                psm.setInt(10, accountId);

                check = psm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, null);
        }
        return check;
    }

    public boolean UpdateRoommateInfo(RoommateInfo roommateInfo) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                String UPDATE_ROOMMATE_INFO =
                        "UPDATE [dbo].[RoomateInformations]\n" +
                                "SET fullname = ?, email = ?, birthday = ?, sex = ?, phone = ?,\n" +
                                "address = ?, identity_card_number = ?, parent_name = ?, parent_phone = ?\n" +
                                "WHERE roomate_info_id = ?";
                psm = conn.prepareStatement(UPDATE_ROOMMATE_INFO);
                psm.setString(1, roommateInfo.getInformation().getFullname());
                psm.setString(2, roommateInfo.getInformation().getEmail());
                psm.setString(3, roommateInfo.getInformation().getBirthday());
                psm.setInt(4, roommateInfo.getInformation().getSex());
                psm.setString(5, roommateInfo.getInformation().getPhone());
                psm.setString(6, roommateInfo.getInformation().getAddress());
                psm.setString(7, roommateInfo.getInformation().getCccd());
                psm.setString(8, roommateInfo.getParentName());
                psm.setString(9, roommateInfo.getParentPhone());
                psm.setInt(10, roommateInfo.getRoommateID());

                check = psm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, null);
        }
        return check;
    }

    public boolean DeleteRoommateInfo(int accountId, int roommateId) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                String DELETE_ROOMMATE_INFO = "DELETE FROM RoomateInformations WHERE account_renter_id = ? AND roomate_info_id = ?";
                psm = conn.prepareStatement(DELETE_ROOMMATE_INFO);
                psm.setInt(1, accountId);
                psm.setInt(2, roommateId);

                check = psm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, null);
        }
        return check;
    }

    public boolean DeleteRoommateInfo(int roomID) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                psm = conn.prepareStatement("DELETE FROM RoomateInformations WHERE roomate_info_id = ?");
                psm.setInt(1, roomID);

                check = psm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, null);
        }
        return check;
    }
}
