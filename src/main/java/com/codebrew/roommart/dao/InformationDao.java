package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.*;
import java.time.format.DateTimeFormatter;

public class InformationDao {

    private static final String IS_EXIST_EMAIL =
            "SELECT A.email FROM AccountInformations A JOIN Accounts B ON A.account_id = B.account_id \n" +
                    "WHERE A.email = ? AND (B.status = -1 OR B.status = 1 OR B.status = 0)";


    public boolean isExistEmail(String email) {
        boolean check = false;
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                psm = conn.prepareStatement(IS_EXIST_EMAIL);
                psm.setString(1, email);
                rs = psm.executeQuery();
                if (rs != null && rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (psm != null) {
                    psm.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return check;
    }
    
    private static final String GET_HOSTEL_OWNER_INFO_BY_HOSTEL_ID =
            "SELECT a.fullname, a.birthday," +
                    "a.sex, a.phone, a.address, a.identity_card_number\n" +
                    "FROM AccountInformations a JOIN Hostels h ON a.account_id = h.owner_account_id\n" +
                    "WHERE h.hostel_id= ?";
    private static final String GET_RENTER_INFO_BY_ID =
            "SELECT *\n" +
                    "FROM AccountInformations\n" +
                    "WHERE account_id = ?";

    private static final String GET_RENTER_INFO_BY_EMAIL =
            "SELECT *\n" +
                    "FROM AccountInformations\n" +
                    "WHERE email = ?";
    private static final String UPDATE_PROFILE =
            "UPDATE AccountInformations\n" +
                    "SET fullname = ?, email = ?, birthday = ?, phone = ?, " +
                    "address = ?, identity_card_number = ?, sex = ?\n" +
                    "WHERE account_id = ?";
    //-------------------------------------Method-----------------------------------------
    public Information getHostelOwnerInfoByHostelId(int hostelId) throws SQLException {
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

        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_INFO_BY_ID);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String email = rs.getString("email");
                    String birthday = rs.getString("birthday");
                    int sex = rs.getInt("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");
                    inf = Information.builder().account_id(rs.getInt("account_id"))
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
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return inf;
    }

    public boolean updateProfileByAccId(Information infor,int accId) throws SQLException {
        boolean checkUpdate = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(UPDATE_PROFILE);
                ptm.setString(1, infor.getFullname());
                ptm.setString(2, infor.getEmail());
                ptm.setString(3, infor.getBirthday());
                ptm.setString(4, infor.getPhone());
                ptm.setString(5, infor.getAddress());
                ptm.setString(6, infor.getCccd());
                ptm.setInt(7, infor.getSex());
                ptm.setInt(8, accId);

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

    public Information getAccountInformationByEmail(String email) {
        Connection cn = null;
        PreparedStatement pst = null;
        Information inf = null;

        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_INFO_BY_EMAIL);
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String birthday = rs.getString("birthday");
                    int sex = rs.getInt("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");
                    inf = Information.builder().account_id(rs.getInt("account_id"))
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
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return inf;
    }

    public boolean updateOwnerProfileByAccId(Information accountInfos,int accId) throws SQLException {
        System.out.println(accountInfos.getBirthday().toString());
        System.out.println(java.sql.Date.valueOf(accountInfos.getBirthday()));
        boolean checkUpdate = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement("UPDATE AccountInformations SET fullname = ?, email = ?, birthday = ?, phone = ?, address = ?, identity_card_number = ?, sex = ? WHERE account_id = ?");
                ptm.setString(1, accountInfos.getFullname());
                ptm.setString(2, accountInfos.getEmail());
                ptm.setString(3, accountInfos.getBirthday());
                ptm.setString(4, accountInfos.getPhone());
                ptm.setString(5, accountInfos.getAddress());
                ptm.setString(6, accountInfos.getCccd());
                ptm.setInt(7, accountInfos.getSex());
                ptm.setInt(8, accId);

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
            OwnerUtils.closeSQL(cn, ptm, null);
        }
        return checkUpdate;
    }
}





