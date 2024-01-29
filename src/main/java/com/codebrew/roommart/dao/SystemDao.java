package com.codebrew.roommart.dao;


import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


public class SystemDao {

    public Account getAccountByEmail(String email){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("select email from accounts where email = ?");
                pst.setString(1, email);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    acc = new Account().builder()
                            .email(rs.getString("email"))
                            .build();
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
        return acc;
    }

    public  Account getAccountByToken(String token){
        return null;
    }

    public boolean updateRecoverCode(String mail, String code, Timestamp expired_date){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean isSuccess = false;
        String sql = "update accounts set request_recover_password_code = ?, expired_time_recover_password = ? where email = ? ";
        try {
            cn = DatabaseConnector.makeConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, code);
            pst.setTimestamp(2, expired_date);
            pst.setString(3, mail);
            int rowsAffected = pst.executeUpdate();
            isSuccess = (rowsAffected > 0);

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
        return isSuccess;
    }

    public boolean resetPassword(String code, String pass){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean check = false;

        String sql = "update accounts set password = ?, " +
                    "expired_time_recover_password = null " +
                    "where request_recover_password_code = ? "
                    +"RETURNING account_id";
        try {
            cn = DatabaseConnector.makeConnection();
            pst = cn.prepareStatement(sql);
            pst.setString(1, pass);
            pst.setString(2, code);
            rs = pst.executeQuery();
            if (rs != null && rs.next()){
                check = true;
            }

        } catch (Exception e) {
            System.out.println(e);
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
        return check;
    }

    String UPDATE_ACCOUNT_INFORMATION = "UPDATE accountinformations "
            + "SET fullname = ?, birthday = ?, phone = ?, address = ?,  identity_card_number = ? "
            + "WHERE account_id = ?";
    public boolean updateUserInformation(Account acc){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean isSuccess = false;
        UserInformation info = acc.getAccountInfo();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(UPDATE_ACCOUNT_INFORMATION);
                pst.setString(1, info.getFullname());
                pst.setString(2, info.getBirthday());
                pst.setString(3, info.getPhone());
                pst.setString(4, info.getAddress());
                pst.setString(5, info.getCccd());
                pst.setInt(6, acc.getAccId());
                int rowsAffected = pst.executeUpdate();
                isSuccess = (rowsAffected > 0);
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
        return isSuccess;
    }

    String GET_ACCOUNT_BY_UNAME_PASS = "SELECT ai.*, a.role , a.status "
            + "FROM accounts a "
            + "JOIN accountinformations ai ON a.account_id = ai.account_id "
            + "WHERE a.email = ? AND a.password = ?";
    public Account getAccountByUsernameAndPassword(String uname, String psw){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_ACCOUNT_BY_UNAME_PASS);
                pst.setString(1, uname);
                pst.setString(2, psw);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    UserInformation info = new UserInformation().builder()
                                            .fullname(rs.getString("fullname"))
                                            .birthday(rs.getDate("birthday").toString())
                                            .address(rs.getString("address"))
                                            .phone(rs.getString("phone"))
                                            .cccd(rs.getString("identity_card_number"))
                                            .sex(rs.getBoolean("sex"))
                                            .build();
                    acc = new Account().builder().
                            accId(rs.getInt("account_id"))
                            .email(uname)
                            .password(psw)
                            .role(rs.getInt("Role"))
                            .status(rs.getInt("status"))
                            .accountInfo(info)
                            .build();
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
        return acc;
    }
    public boolean checkEmailAvaiable(String mail){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Accounts WHERE email = ?";

        boolean isSuccess = true;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, mail);
                rs = pst.executeQuery();
                isSuccess = !rs.next();
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
        return isSuccess;
    }

    public boolean checkOTP(String token, String otp){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select * from accounts where token = ? and otp = ?";

        boolean isSuccess = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, token);
                pst.setString(2, otp);
                rs = pst.executeQuery();
                isSuccess = rs.next();
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
        return isSuccess;
    }

    public boolean resAddEmailTokenOtp(String mail, String token, String otp){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "INSERT INTO " +
                     "Accounts (email, token, otp) " +
                     "VALUES (?, ?, ?)";

        boolean isSuccess = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, mail);
                pst.setString(2, token);
                pst.setString(3, otp);
                int rowsAffected = pst.executeUpdate();
                isSuccess = (rowsAffected > 0);
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
        return isSuccess;
    }

    private static final String RES_UPDATE_ACCOUNT = "UPDATE Accounts "
                                    + "SET password = ?, token = '', expired_time_recover_password = '' , role = 1, status = 1,  otp = ''"
                                    + "WHERE token = ? "
                                    + "RETURNING account_id, email";
    private static final String RES_UPDATE_USERINFORMATION = "INSERT INTO AccountInformations (account_id, fullname, birthday, sex, phone, address, identity_card_number) "
            + "values (?, ?, ?, ?, ?, ?, ?)";

    public boolean resAddInformation(UserInformation info, int acc_id, String email){
        Connection cn = null;
        PreparedStatement pst = null;
        boolean sts = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(RES_UPDATE_USERINFORMATION);
                pst.setInt(1, acc_id);
                pst.setString(2, info.getFullname());
                pst.setString(3, info.getBirthday());
                pst.setBoolean(4, info.isSex());
                pst.setString(5, info.getPhone());
                pst.setString(6, info.getAddress());
                pst.setString(7, info.getCccd());
                int rowsAffected = pst.executeUpdate();
                sts = (rowsAffected > 0);
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
        return sts;
    }

    public Account resAddAccount(UserInformation user_info, String token, String password){
        Connection cn = null;
        PreparedStatement pst = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(RES_UPDATE_ACCOUNT);
                pst.setString(1, password);
                pst.setString(2, token);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int account_id = rs.getInt("account_id");
                    String email = rs.getString("email");
                    LocalDate currentDate = LocalDate.now();
                    if (resAddInformation(user_info, account_id, email));
                    acc = new Account(account_id, email, password, "", currentDate.toString(), 1, 1, user_info);
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
        return acc;
    }

    private static final String CHECK_ACCOUNT_REQUEST_RECOVER_PASSWORD = "select * from accounts where email = ? and request_recover_password_code = ? and expired_time_recover_password > NOW() - INTERVAL '24 hours'";

    public boolean checkAccountRequestRecoverPassword(String email, String code){
        Connection cn = null;
        PreparedStatement pst = null;
        boolean status = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(CHECK_ACCOUNT_REQUEST_RECOVER_PASSWORD);
                pst.setString(1, email);
                pst.setString(2, code);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    status = true;
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
        return status;
    }
}
