package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Roommate;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoommateInfoDAO {
    private static final String GET_LIST_ROOMMATES_BY_RENTER_ID =
            "SELECT ri.roomate_info_id, ri.fullname, ri.email, " +
                    "ri.birthday, ri.sex, ri.phone, ri.address, ri.parent_name, ri.parent_phone\n" +
                    "FROM roomateinformations ri\n" +
                    "JOIN contract_main cm ON ri.contract_id = cm.contract_id\n" +
                    "WHERE cm.renter_id = ?";
    private static final String GET_ROOMMATE_BY_ID =
            "SELECT roomate_info_id, fullname, email, birthday," +
                    "sex, phone, address, identity_card_number," +
                    "parent_name, parent_phone, account_renter_id\n" +
                    "FROM RoomateInformations\n" +
                    "WHERE roomate_info_id = ?";
    public List<Roommate> getListRoommatesByRenterID(int renterId) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        List<Roommate> list = new ArrayList<Roommate>();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                psm = conn.prepareStatement(GET_LIST_ROOMMATES_BY_RENTER_ID);
                psm.setInt(1, renterId);
                rs = psm.executeQuery();

                while (rs.next()) {
                    UserInformation information = UserInformation.builder()
                            .fullname(rs.getString("fullname"))
                            .email(rs.getString("email"))
                            .birthday(rs.getDate("birthday").toString())
                            .sex(rs.getBoolean("sex"))
                            .phone(rs.getString("phone"))
                            .address(rs.getString("address"))
                            .build();
                    Roommate roommateInfo = Roommate.builder()
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

    public Roommate getRoommateByID(int roommateID) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        Roommate roommateInfo = null;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                psm = conn.prepareStatement(GET_ROOMMATE_BY_ID);
                psm.setInt(1, roommateID);
                rs = psm.executeQuery();

                if (rs.next()) {
                    UserInformation information = UserInformation.builder()
                            .fullname(rs.getString("fullname"))
                            .email(rs.getString("email"))
                            .birthday(rs.getDate("birthday").toString())
                            .sex(rs.getBoolean("sex"))
                            .phone(rs.getString("phone"))
                            .address(rs.getString("address"))
                            .cccd(rs.getString("identity_card_number"))
                            .build();
                    roommateInfo = Roommate.builder()
                            .roommateID(rs.getInt("roomate_info_id"))
                            .information(information)
                            .parentName(rs.getString("parent_name"))
                            .parentPhone(rs.getString("parent_phone")).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (psm != null) psm.close();
            if (conn != null) conn.close();
        }
        return roommateInfo;
    }
}
