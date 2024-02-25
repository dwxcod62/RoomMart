package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IAccountDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO implements IAccountDAO {
    @Override
    public Account getAccountById(int id) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM Accounts WHERE account_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    acc = new Account().builder().
                            accId(rs.getInt("account_id"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .role(rs.getInt("role"))
                                .status(rs.getInt("status"))
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return acc;
    }

    @Override
    public UserInformation getAccountInformationById(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        UserInformation inf = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT * FROM AccountInformations WHERE account_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, accId);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String email = rs.getString("email");
                    String birthday = rs.getString("birthday");
                    boolean sex = OwnerUtils.convertIntToBoolean(rs.getInt("sex"));
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");
                    inf = new UserInformation(fullname, email, birthday, sex, phone, address, cccd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, null);
        }
        return inf;
    }
}
