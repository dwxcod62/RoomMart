
package com.codebrew.roommart.utils;

import java.sql.*;

public class DatabaseConnector {
    public static Connection makeConnection() throws SQLException {
        Connection connection = null;
        String url = "jdbc:postgresql://ep-misty-glitter-a12t6sai.ap-southeast-1.aws.neon.tech/roomdemo?sslmode=require";
        String username = "ar.snowyy";
        String password = "lXSEpuk8JGv3";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connect success");
        } catch (ClassNotFoundException  | SQLException e) {
            throw new SQLException("Error connecting to database", e);
        }
        return connection;
    }

    public static void main(String[] args) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();

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
    }
}
