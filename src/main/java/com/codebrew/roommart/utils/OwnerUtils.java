package com.codebrew.roommart.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerUtils {
    public static void closeSQL(Connection cn, PreparedStatement pst, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int convertBooleanToInt(boolean bool) {
        if (bool) {
            return 1;
        }
        return 0;
    }

    public static boolean convertIntToBoolean(int num) {
        return num == 1;
    }
}
