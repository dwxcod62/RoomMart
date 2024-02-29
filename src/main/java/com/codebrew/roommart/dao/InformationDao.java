package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InformationDao {
    //------------------------------------SQL QUERY--------------------------------------
    private static final String GET_HOSTEL_OWNER_INFO_BY_HOSTEL_ID =
            "SELECT DISTINCT AccountInformations.fullname, AccountInformations.birthday," +
                    "AccountInformations.sex, AccountInformations.phone, AccountInformations.address, AccountInformations.identity_card_number\n" +
                    "FROM AccountInformations INNER JOIN Hostels ON AccountInformations.account_id=Hostels.owner_account_id\n" +
                    "WHERE Hostels.hostel_id= ?";
    //-------------------------------------Method-----------------------------------------
    public Information getHostelOwnerInfoByHostelId(int hostelId) throws SQLException {
        System.out.println("getHostelOwnerInfoByHostelId");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Information accountInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_OWNER_INFO_BY_HOSTEL_ID);
//                System.out.println(GET_HOSTEL_OWNER_INFO_BY_HOSTEL_ID);
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String birthday = rs.getDate("birthday").toString();
                    int sex = rs.getInt("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");

                    accountInfor = Information.builder()
                            .fullname(fullname)
                            .phone(phone)
                            .birthday(birthday)
                            .sex(sex)
                            .address(address)
                            .cccd(cccd)
                            .build();
                }
            }
//            System.out.println("account owner hostel"+accountInfor);
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
        return accountInfor;
    }
}
