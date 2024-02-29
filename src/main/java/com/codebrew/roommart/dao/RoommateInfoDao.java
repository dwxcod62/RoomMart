package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.dto.RoommateInfo;
import com.codebrew.roommart.utils.DatabaseConnector;

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
            if (rs != null) rs.close();
            if (psm != null) psm.close();
            if (conn != null) conn.close();
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

}
