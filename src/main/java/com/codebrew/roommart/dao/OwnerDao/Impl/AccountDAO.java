package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IAccountDAO;
import com.codebrew.roommart.dao.RoommateInfoDao;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
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
                            .password(rs.getString("password"))
                            .username(rs.getString("username"))
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
    public AccountInfo getAccountInformationById(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        AccountInfo inf = null;
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
                    int sex = rs.getInt("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");
                    inf = new AccountInfo(new Information(fullname, email, birthday, sex, phone, address, cccd));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, null);
        }
        return inf;
    }

    @Override
    public Account getAccountByUsernameAndPassword(String username, String password) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT *\n" +
                        "FROM [dbo].[Accounts]\n" +
                        "WHERE [username] = ? AND [password] = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    acc = getAccount(rs);
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
    public boolean updateAccountPass(int accId, String pass) {
        Connection cn = null;
        PreparedStatement pst = null;
        Account acc = null;
        boolean isSuccess = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String UPDATE_ACCOUNT_PASSWORD = "Update [dbo].[Accounts] Set [password] = ? Where [account_id] = ?";
                String sql = UPDATE_ACCOUNT_PASSWORD;
                pst = cn.prepareStatement(sql);
                pst.setString(1, pass);
                pst.setInt(2, accId);
                if (pst.executeUpdate() < 1) {
                    cn.rollback();
                } else {
                    isSuccess = true;
                    cn.commit();
                }
                cn.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, null);
        }
        return isSuccess;
    }

    private Account getAccount(ResultSet rs) {
        Account acc = null;
        AccountInfo accInf = null;
        List<RoommateInfo> roommateInfoList = new ArrayList<>();
        try {
            int accId = rs.getInt("account_id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String createdate = rs.getString("create_date");
            int status = rs.getInt("status");
            int role = rs.getInt("role");
            int roomId = rs.getInt("room_id");
            if (role == 2) { //Renter
                roommateInfoList = new RoommateInfoDao().getListRoommatesOfAnAccount(accId);
                accInf = getAccountInformationById(accId);
                acc = Account.builder()
                        .accId(accId)
                        .username(username)
                        .password(password)
                        .createDate(createdate)
                        .status(status)
                        .role(role)
                        .roomId(roomId)
                        .accountInfo(accInf)
                        .roommateInfo(roommateInfoList)
                        .build();
            } else {
                accInf = getAccountInformationById(accId);
                acc = Account.builder()
                        .accId(accId)
                        .username(username)
                        .password(password)
                        .createDate(createdate)
                        .status(status)
                        .role(role)
                        .roomId(-1)
                        .accountInfo(accInf)
                        .roommateInfo(null)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acc;
    }
}
