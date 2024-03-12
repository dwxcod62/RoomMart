package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.dto.RoommateInfo;
import com.codebrew.roommart.utils.DatabaseConnector;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountDao {

    private static final String IS_EXIST_USERNAME = "SELECT username FROM [dbo].[Accounts] Where username = ?";
    private static final String ADD_AN_ACCOUNT = "INSERT INTO Accounts (username, password, create_date, status, role) \n" +
            "VALUES (?, ?, GETDATE(), ?, ?)";
    private static final String ADD_ACCOUNT_INFORMATION = "INSERT INTO AccountInformations (account_id, fullname, email, identity_card_number, birthday, sex, phone, address) \n" +
            "VALUES (?, ?, ?, ?, ?, 1, ?, ?)";

    private static final String ADD_OTP_AND_EXPIRED = "UPDATE accounts " +
            "SET otp = ? , expired_time_otp = DATEADD(HOUR, ?, GETDATE()) " +
            "WHERE username = ?;";

    private Account getAccount(ResultSet rs) {
        Account acc = null;
        AccountInfo accInf = null;
        List<RoommateInfo> roommateInfoList = new ArrayList<>();
        try {
            int accId = rs.getInt("account_id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String createdate = rs.getString("create_date");
            String expired_date = rs.getString("expired_date");
            int recently_Room = rs.getInt("room_id");
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
                        .expiredDate(expired_date)
                        .recentlyRoom(recently_Room)
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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

    public AccountInfo getAccountInformationById(int accId) {

        Connection cn = null;
        PreparedStatement pst = null;
        AccountInfo inf = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT *\n" +
                        "FROM [dbo].[AccountInformations]\n" +
                        "WHERE [account_id] = ?";
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
                    int account_id = rs.getInt("account_id");

                    inf = new AccountInfo(new Information(fullname, email, birthday, sex, phone, address, cccd,account_id));

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

    public Account getAccountByToken(String token) {
        Connection cn = null;
        PreparedStatement pst = null;
        Account acc = null;

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
                    acc = getAccount(rs);
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


    public boolean updateTokenByUserName(String token, String username) {
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sqlUpdateStatus = "Update [dbo].[Accounts]\n" +
                        "Set token = ?\n" +
                        "Where username = ?";
                pst = cn.prepareStatement(sqlUpdateStatus);
                pst.setString(1, token);
                pst.setString(2, username);
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

    public boolean isExistUsername(String username) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(IS_EXIST_USERNAME);
                pst.setString(1, username);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }


    public boolean addOtpAndExpiredTime(String otp, int hour, int account_id){
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(ADD_OTP_AND_EXPIRED);

                pst.setString(1, otp);
                pst.setInt(2, hour);
                pst.setInt(3, account_id);

                if (pst.executeUpdate() > 0) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;

    }

    public boolean addAnAccount(Account account) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null){

                pst = cn.prepareStatement(ADD_AN_ACCOUNT, Statement.RETURN_GENERATED_KEYS);

                pst.setString(1, account.getUsername());
                pst.setString(2, account.getPassword());
                pst.setInt(3, account.getStatus());
                pst.setInt(4, account.getRole());

                if (pst.executeUpdate() > 0) {
                    int accountId = -1;
                    rs = pst.getGeneratedKeys();

                    if (rs.next()) {
                        accountId = rs.getInt(1);
                    }
                    pst = cn.prepareStatement(ADD_ACCOUNT_INFORMATION);
                    pst.setInt(1, accountId);
                    pst.setString(2, account.getAccountInfo().getInformation().getFullname());
                    pst.setString(3, account.getAccountInfo().getInformation().getEmail());
                    pst.setString(4, account.getAccountInfo().getInformation().getCccd());

                    Date date = Date.valueOf(account.getAccountInfo().getInformation().getBirthday());

                    pst.setDate(5, date);
                    pst.setString(6, account.getAccountInfo().getInformation().getPhone());
                    pst.setString(7, account.getAccountInfo().getInformation().getAddress());


                    if (pst.executeUpdate() > 0) {
                        check = true;
                    }

                }
            }
        } catch (Exception e){

        }
        return check;
    }

    public boolean addOTPToAccount(String username, String otp) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null){

                pst = cn.prepareStatement(ADD_OTP_AND_EXPIRED);

                pst.setString(1, otp);
                pst.setInt(2, 1);
                pst.setString(3, username);

                if (pst.executeUpdate() > 0) {
                    check = true;
                }
            }
        } catch (Exception e){

        }
        return check;
    }

    public List<Account> GetAccountsByRole(int role) {
        Account acc;
        ArrayList<Account> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT * \n" +
                        "FROM [dbo].[Accounts] \n" +
                        "WHERE Role = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, role);
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    acc = getAccount(rs);
                    list.add(acc);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public int checkAccountByOTP(String email, String otp) throws SQLException {
        Connection conn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;

        int accountId = -1;

        try {

            conn = DatabaseConnector.makeConnection();

            if (conn != null) {
                String sql = "SELECT A.[account_id]\n" +
                        "FROM [dbo].[Accounts] AS A JOIN [dbo].[AccountInformations] AS AI ON A.[account_id] = AI.[account_id]\n" +
                        "WHERE AI.[email] = ? AND A.[otp] = ? AND GETDATE() < A.[expired_time_otp]";
                psm = conn.prepareStatement(sql);
                psm.setString(1, email);
                psm.setString(2, otp);
                rs = psm.executeQuery();

                if (rs != null && rs.next()) {
                    accountId = rs.getInt("account_id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) { rs.close(); }
            if (psm != null) { psm.close(); }
            if (conn != null) { conn.close(); }
        }
        return accountId;
    }

    public Account getAccountById(int id) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT *\n" +
                        "FROM [dbo].[Accounts]\n" +
                        "WHERE [account_id] = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    acc = getAccount(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
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
    // Get all account:

    public List<Account> GetAccountsBy2Role(int role1, int role2) {
        Account acc;
        ArrayList<Account> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT * \n" +
                        "FROM [dbo].[Accounts] \n" +
                        "WHERE Role IN (?, ?)"; // Sử dụng IN để lấy các role là role1 hoặc role2
                pst = cn.prepareStatement(sql);
                pst.setInt(1, role1); // Thiết lập tham số cho role1
                pst.setInt(2, role2); // Thiết lập tham số cho role2
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    acc = getAccount(rs);
                    list.add(acc);
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
        return list;
    }
    // Update status
    public boolean updateAccountStatus(int id, int status) {
        Connection cn = null;
        PreparedStatement pst = null;
        Account acc = null;
        boolean result = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "Update [dbo].[Accounts]\n" +
                        "Set status = ?\n" +
                        "Where account_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, status);
                pst.setInt(2, id);
                int i = pst.executeUpdate();
                if(i > 0) result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cn != null && pst != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public int GetAccountsByRoleInRecentMonth(int role) {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int ngayDauThang = 1;
        int ngayCuoiThang = currentDate.lengthOfMonth(); // Lấy ngày cuối cùng của tháng

        String startDate = currentYear + "-" + currentMonth + "-" + ngayDauThang;
        String endDate = currentYear + "-" + currentMonth + "-" + ngayCuoiThang;

        int count = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT COUNT(*) FROM [dbo].[Accounts] WHERE role = ? and create_date between ? and ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, role);
                pst.setString(2, startDate);
                pst.setString(3, endDate);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return count;

    }

    public int getStatusOfAccount(String email) {
        int status = -2;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT a.[status] \n" +
                        "FROM [Accounts] a \n" +
                        "JOIN [AccountInformations] ai ON a.account_id = ai.account_id \n" +
                        "WHERE ai.email = ?;";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs.next()) {
                    status = rs.getInt("status");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return status;

    }

    public boolean updateAccountStatusByEmail(String email, int st){
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                String sql = "UPDATE [Accounts]\n" +
                        "SET status = ?\n" +
                        "FROM [Accounts] a\n" +
                        "JOIN AccountInformations ai ON a.account_id = ai.account_id \n" +
                        "WHERE ai.email = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, st);
                pst.setString(2, email);

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public  Integer getRoomOfRenter(String email) {
        Integer status = 0;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "select a.room_id from [Accounts] a\n" +
                        "join [AccountInformations] ai on a.account_id = ai.account_id\n" +
                        "where email = ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs.next()) {
                    status = rs.getInt("room_id");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return status;
    }

    public boolean updateRoomForAccount(int account_id, int room_id) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                pst = cn.prepareStatement("update [Accounts]\n" +
                        "set room_id = ?\n" +
                        "where account_id = ?");

                pst.setInt(1, room_id);
                pst.setInt(2, account_id);

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public boolean updateNULLRoomForAccount(int account_id) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                pst = cn.prepareStatement("update [Accounts]\n" +
                        "set room_id = NULL\n" +
                        "where account_id = ?");

                pst.setInt(1, account_id);

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public int getOwnerIdByRoomId(int room_id) {
        int id = -1;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                pst = cn.prepareStatement("select a.account_id from Accounts a\n" +
                        "join Hostels h on h.owner_account_id = a.account_id\n" +
                        "join Rooms r on r.hostel_id = h.hostel_id\n" +
                        "where r.room_id = ?");

                pst.setInt(1, room_id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    id = rs.getInt("account_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }


}

