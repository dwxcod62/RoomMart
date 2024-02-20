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
import java.util.Date;
import java.util.List;

public class UserInformationDAO {
    private static final String GET_HOSTEL_OWNER_INFO_BY_RENTER_ID =
            "SELECT DISTINCT AccountInformations.fullname, AccountInformations.birthday," +
                    "AccountInformations.sex, AccountInformations.phone, AccountInformations.address, AccountInformations.identity_card_number\n" +
                    "FROM AccountInformations INNER JOIN Accounts ON AccountInformations.account_id=Accounts.account_id\n" +
                    "INNER JOIN Hostels ON Accounts.account_id=Hostels.owner_account_id\n" +
                    "INNER JOIN Contracts ON Accounts.account_id=Contracts.hostel_owner_id\n" +
                    "WHERE Contracts.renter_id= ?";
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
            "SET fullname = ?, email = ?, birthday = ?, phone = ?," +
            "address = ?, identity_card_number = ?, sex = ?\n" +
            "WHERE account_id = ?";
    private static final String UPDATE_ROOMMATE =
            "UPDATE RoomateInformations\n" +
            "SET fullname = ?, email = ?, birthday = ?, sex = ?, phone = ?, address = ?, identity_card_number = ?\n" +
            "WHERE roomate_info_id = ?";


    public UserInformation getHostelOwnerInfoByHostelId(int hostelId) throws SQLException {
        System.out.println("getHostelOwnerInfoByHostelId");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserInformation accountInfor = null;
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
                    boolean sex = rs.getBoolean("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");

                    accountInfor = UserInformation.builder()
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
    public UserInformation getHostelOwnerInfoByRenterId(int renterId) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserInformation accountInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_OWNER_INFO_BY_RENTER_ID);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String birthday = rs.getDate("birthday").toString();
                    boolean sex = rs.getBoolean("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");

                    accountInfor = UserInformation.builder()
                            .fullname(fullname)
                            .phone(phone)
                            .birthday(birthday)
                            .sex(sex)
                            .address(address)
                            .cccd(cccd)
                            .build();
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
        return accountInfor;
    }

    public UserInformation getAccountInformationById(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        UserInformation inf = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_INFO_BY_ID);
                pst.setInt(1, renterId);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String birthday = rs.getDate("birthday").toString();
                    boolean sex = rs.getBoolean("sex");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");
                    inf = new UserInformation(fullname, null, birthday, sex, phone, address, cccd, rs.getInt("account_id"), null);
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
    public List<UserInformation> getListAccountInformationById(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        UserInformation inf = null;

        List<UserInformation> listUserInfo = new ArrayList<UserInformation>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_INFO_BY_ID);
                pst.setInt(1, renterId);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    UserInformation userInfo = UserInformation.builder()
                            .fullname(rs.getString("fullname"))
                            .email(rs.getString("email"))
                            .birthday(rs.getDate("birthday").toString())
                            .sex(rs.getBoolean("sex"))
                            .phone(rs.getString("phone"))
                            .address(rs.getString("address"))
                            .cccd(rs.getString("identity_card_number"))
                            .build();
                    listUserInfo.add(userInfo);
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
        return listUserInfo;
    }
    public boolean updateProfileByAccId(UserInformation accountInfos,int accId) throws SQLException {
        boolean checkUpdate = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(UPDATE_PROFILE);
                ptm.setString(1, accountInfos.getFullname());
                ptm.setString(2, accountInfos.getEmail());
                String utilDate = accountInfos.getBirthday();
                ptm.setString(3, utilDate);
                ptm.setString(4, accountInfos.getPhone());
                ptm.setString(5, accountInfos.getAddress());
                ptm.setString(6, accountInfos.getCccd());
                ptm.setBoolean(7, accountInfos.isSex());
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
    public boolean updateRoommateInfoByID(Roommate roommateInfo, int roommateID) throws SQLException {
        boolean checkUpdate = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(UPDATE_ROOMMATE);
                ptm.setString(1, roommateInfo.getInformation().getFullname());
                ptm.setString(2, roommateInfo.getInformation().getEmail());

                // Chuyển đổi java.util.Date thành java.sql.Date
                String utilDate = roommateInfo.getInformation().getBirthday();
                // Sử dụng java.sql.Date trong PreparedStatement
                ptm.setString(3, utilDate);

                ptm.setBoolean(4, roommateInfo.getInformation().isSex());
                ptm.setString(5, roommateInfo.getInformation().getPhone());
                ptm.setString(6, roommateInfo.getInformation().getAddress());
                ptm.setString(7, roommateInfo.getInformation().getCccd());
                ptm.setInt(8, roommateID);

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
