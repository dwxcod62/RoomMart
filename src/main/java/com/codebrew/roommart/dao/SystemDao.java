package com.codebrew.roommart.dao;


import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import org.json.JSONObject;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


public class SystemDao {


    private java.sql.Date convertToDate(String date_string ){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(date_string);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            return sqlDate;
        } catch ( Exception e){
            return null;
        }
    }

    private static final String UPDATE_CONTRACT_RENTER_SIGN = "UPDATE contract_details\n" +
            "SET renter_sign = ?\n" +
            "FROM contract_main\n" +
            "WHERE contract_main.contract_details_id = contract_details.contract_details_id\n" +
            "AND contract_main.renter_id = ? AND contract_main.owner_id = ? ";

    private static final String UPDATE_CONTRACT_OWNER_SIGN = "UPDATE contract_details\n" +
            "SET owner_sign = ?\n" +
            "FROM contract_main\n" +
            "WHERE contract_main.contract_details_id = contract_details.contract_details_id\n" +
            "AND contract_main.renter_id = ? AND contract_main.owner_id = ? ";

    public boolean updateContractSign(String owner_mail, String renter_mail, int role,  String sign){
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            AccountDao dao = new AccountDao();
            if (cn != null) {
                if ( role == 1 ){
                    pst = cn.prepareStatement(UPDATE_CONTRACT_OWNER_SIGN);
                } else {
                    pst = cn.prepareStatement(UPDATE_CONTRACT_RENTER_SIGN);
                }
                pst.setString(1, sign);
                pst.setString(2, renter_mail);
                pst.setString(3, owner_mail);

                int rowsAffected = pst.executeUpdate();
                result = (rowsAffected > 0);
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


    private static final String UPDATE_CONTRACT_OWNER_SIDE =
            "INSERT INTO contract_details ( owner_full_name, owner_birthday, owner_address, owner_identify_card, owner_phone, \n" +
            "renter_full_name, renter_birthday, renter_phone, renter_identify_card, month_per_pay, \n" +
            "cost_per_month, deposit, start_date, end_date, current_day )\n" +
            "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) returning contract_details_id " ;

    private  static final String UPDATE_CONTRACT_MAIN_OWNER_SIDE = "INSERT INTO contract_main ( contract_details_id, contract_status, owner_id, renter_id, room_id)\n" +
            "values ( ?, ?, ?, ?, ?) returning contract_id";

    public boolean updateContractOwnerSide(JSONObject json){
        boolean result = false;
        Connection cn = null;
        PreparedStatement pst = null;

        try {
            cn = DatabaseConnector.makeConnection();
            AccountDao dao = new AccountDao();
            if (cn != null) {
                pst = cn.prepareStatement(UPDATE_CONTRACT_OWNER_SIDE);

                UserInformation owner_info = dao.getInfoByMailForContact(json.getString("owner_mail"));
                pst.setString(1, owner_info.getFullname());
                pst.setDate(2, convertToDate(owner_info.getBirthday()));
                pst.setString(3, owner_info.getAddress());
                pst.setString(4, owner_info.getCccd());
                pst.setString(5, owner_info.getPhone());

                UserInformation renter_info = dao.getInfoByMailForContact(json.getString("renter_mail"));
                pst.setString(6, owner_info.getFullname());
                pst.setDate(7, convertToDate(owner_info.getBirthday()));
                pst.setString(8, owner_info.getPhone());
                pst.setString(9, owner_info.getCccd());

                pst.setInt(10, json.getInt("payment_term"));
                pst.setInt(11, json.getInt("room_fee"));
                pst.setInt(12, json.getInt("room_deposit"));
                pst.setDate(13, convertToDate(json.getString("room_start_date")));
                pst.setDate(14, convertToDate(json.getString("room_end_date")));
                pst.setDate(15, convertToDate(json.getString("room_end_date")));

                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()){
                    int contract_details_id = rs.getInt("contract_details_id");
                    pst = cn.prepareStatement(UPDATE_CONTRACT_MAIN_OWNER_SIDE);
                    pst.setInt(1, contract_details_id);
                    pst.setInt(2, 1);
                    pst.setString(3, json.getString("owner_mail"));
                    pst.setString(4, json.getString("renter_mail"));
                    pst.setInt(5, 1);

                    ResultSet rs_2 = pst.executeQuery();
                    if (rs_2 != null && rs_2.next()){
                        int contract_id = rs_2.getInt("contract_id");
                        result = true;
                    }
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




    private static final String GET_CONTRACT_INFORMATION_BY_EMAIL = "SELECT cd.* \n" +
            "FROM contract_main cm \n" +
            "JOIN contract_details cd ON cm.contract_details_id = cd.contract_details_id \n" +
            "WHERE cm.owner_id = ? AND cm.renter_id = ?;";

    public JSONObject getContractInformationByEmail(String owner_mail, String renter_email){
        JSONObject jsonObject = new JSONObject();;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_CONTRACT_INFORMATION_BY_EMAIL);
                pst.setString(1, owner_mail);
                pst.setString(2, renter_email);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    jsonObject.put("current_day", rs.getDate("current_day").toString());

                    jsonObject.put("owner_full_name", rs.getString("owner_full_name"));
                    jsonObject.put("owner_phone", rs.getString("owner_phone"));
                    jsonObject.put("owner_identify_card", rs.getString("owner_identify_card"));
                    jsonObject.put("owner_address", rs.getString("owner_address"));
                    jsonObject.put("owner_birthday", rs.getDate("owner_birthday").toString());
                    jsonObject.put("owner_sign", rs.getString("owner_sign"));

                    jsonObject.put("renter_full_name", rs.getString("renter_full_name"));
                    jsonObject.put("renter_phone", rs.getString("renter_phone"));
                    jsonObject.put("renter_identify_card", rs.getString("renter_identify_card"));
                    jsonObject.put("renter_birthday", rs.getDate("renter_birthday").toString());

                    jsonObject.put("room_start_date", rs.getDate("start_date").toString());
                    jsonObject.put("room_end_date", rs.getDate("end_date").toString());
                    jsonObject.put("room_fee", rs.getDouble("cost_per_month"));
                    jsonObject.put("payment_term", rs.getInt("month_per_pay"));
                    jsonObject.put("room_deposit", rs.getDouble("deposit"));
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
        return jsonObject;
    }

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

    public boolean checkOTP(String mail, String otp){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "select * from accounts where email = ? and otp = ?";

        boolean isSuccess = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, mail);
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

    public boolean resAddEmailOtp(String mail, String otp){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "INSERT INTO " +
                     "Accounts (email, otp) " +
                     "VALUES (?, ?)";

        boolean isSuccess = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, mail);
                pst.setString(2, otp);
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
                                    + "SET password = ?, token = '' , role = ?, status = 1,  otp = '' "
                                    + "WHERE email = ? "
                                    + "RETURNING account_id";
    private static final String RES_UPDATE_USERINFORMATION = "INSERT INTO AccountInformations (account_id, fullname, birthday, sex, phone, address, identity_card_number) "
            + "values (?, ?, ?, ?, ?, ?, ?)";

    public boolean resAddInformation(UserInformation info, int acc_id, String email){
        Connection cn = null;
        PreparedStatement pst = null;
        boolean sts = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(info.getBirthday());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                pst = cn.prepareStatement(RES_UPDATE_USERINFORMATION);
                pst.setInt(1, acc_id);
                pst.setString(2, info.getFullname());
                pst.setDate(3, sqlDate );
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

    public Account resAddAccount(UserInformation user_info, String email, String password, int role){
        Connection cn = null;
        PreparedStatement pst = null;
        Account acc = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(RES_UPDATE_ACCOUNT);
                pst.setString(1, password);
                pst.setInt(2, role);
                pst.setString(3, email);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int account_id = rs.getInt("account_id");
                    LocalDate currentDate = LocalDate.now();
                    if (resAddInformation(user_info, account_id, email)){
                        acc = new Account(account_id, email, password, "", currentDate.toString(), 1, role, user_info);
                    } else {
                        acc = null;
                        System.out.println("Loi o resAddAccount");
                    }
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
