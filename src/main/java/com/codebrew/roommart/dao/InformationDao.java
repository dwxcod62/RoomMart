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
    private static final String GET_RENTER_INFO_BY_ID =
            "SELECT *\n" +
                    "FROM AccountInformations\n" +
                    "WHERE account_id = ?";
    private static final String UPDATE_PROFILE =
            "UPDATE AccountInformations\n" +
                    "SET fullname = ?\n" +
                    "WHERE account_id = ?";
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

    public Information getAccountInformationById(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        Information inf = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_INFO_BY_ID);
                pst.setInt(1, renterId);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String email = rs.getString("email");
                    String birthday = rs.getString("birthday");
                    int sex = rs.getInt("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");

                    inf = Information.builder()
                            .fullname(fullname)
                            .email(email)
                            .birthday(birthday)
                            .sex(sex)
                            .phone(phone)
                            .address(address)
                            .cccd(cccd)
                            .build();
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
        return inf;
    }

    public boolean updateProfileByAccId(Information accountInfos,int accId) throws SQLException {
        boolean checkUpdate = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(UPDATE_PROFILE);
                ptm.setString(1, accountInfos.getFullname());
//                ptm.setString(2, accountInfos.getEmail());
//                ptm.setString(3, accountInfos.getBirthday());
//                ptm.setString(4, accountInfos.getPhone());
//                ptm.setString(5, accountInfos.getAddress());
//                ptm.setString(6, accountInfos.getCccd());
//                ptm.setInt(7, accountInfos.getSex());
                ptm.setInt(2, accId);

                checkUpdate = ptm.executeUpdate() > 0;

                if (!checkUpdate) {
                    cn.rollback();
                } else {
                    cn.commit();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return checkUpdate;
    }
}
