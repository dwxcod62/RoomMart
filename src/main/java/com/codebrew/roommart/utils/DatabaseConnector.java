
package com.codebrew.roommart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection makeConnection() throws SQLException {
        Connection connection = null;
        String url = "jdbc:postgresql://ep-silent-lake-76600314.ap-southeast-1.aws.neon.tech/RoomRetelHub?sslmode=require";
        String username = "Thanhhoacam";
        String password = "PUg7kGetso2u";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connect success");
        } catch (ClassNotFoundException  | SQLException e) {
            throw new SQLException("Error connecting to database", e);
        }
        return connection;
    }
}
