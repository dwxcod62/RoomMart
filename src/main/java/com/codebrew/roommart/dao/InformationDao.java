package com.codebrew.roommart.dao;

import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

}
