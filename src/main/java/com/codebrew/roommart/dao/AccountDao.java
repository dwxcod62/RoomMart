package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Roommate;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {


    private static final String CHECK_ACCOUNT_EXIST_BY_ROLE = "select account_id from accounts where email = ? and role = ?";
    public boolean checkAccountExistByRole(String email, int role){
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(CHECK_ACCOUNT_EXIST_BY_ROLE);
                pst.setString(1, email);
                pst.setInt(2, role);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cn != null && pst != null) {
                try {
                    pst.close();
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public  Account getAccountByToken(String token){
        Account acc = null;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT *\n" +
                        "FROM [dbo].[Accounts]\n" +
                        "WHERE [token] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, token);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
//                    acc = getAccount(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return acc;
    }

    public boolean updateTokenByUserName(String token, String email){
        return false;
    }


    private static final String GET_INFO_FOR_CONTRACT =
            "SELECT ai.account_id, ai.fullname, ai.birthday, ai.phone, ai.identity_card_number, ai.address  \n" +
            "FROM accountinformations AS ai\n" +
            "JOIN accounts AS ac ON ai.account_id = ac.account_id\n" +
            "WHERE ac.email = ?";

    public UserInformation getInfoByMailForContact(String email){
        UserInformation info = null;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_INFO_FOR_CONTRACT);
                pst.setString(1, email);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    info = UserInformation.builder().account_id(rs.getInt("account_id"))
                            .fullname(rs.getString("fullname"))
                            .address(rs.getString("address"))
                            .cccd(rs.getString("identity_card_number"))
                            .phone(rs.getString("phone"))
                            .birthday(rs.getDate("birthday").toString())
                            .build();
                } else {
                    System.out.println("LAY THONG TIN THAT BAI O getInfoByMailForContact trong AccountDao");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cn != null && pst != null) {
                try {
                    pst.close();
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return info;
    }

    private static final String UPDATE_TOKEN_BY_EMAIL =
            "Update accounts " +
                    "Set token = ? " +
                    "Where email = ?";

    public boolean updateTokenByEmail(String token, String email ){
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(UPDATE_TOKEN_BY_EMAIL);
                pst.setString(1, token);
                pst.setString(2, email);
                result = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cn != null && pst != null) {
                try {
                    pst.close();
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
