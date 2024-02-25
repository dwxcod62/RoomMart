
package com.codebrew.roommart.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection makeConnection() throws SQLException {
        Connection connection = null;
        String url = "jdbc:postgresql://ep-empty-glitter-70711314.ap-southeast-1.aws.neon.tech/Roommart?user=tienthanh170803&password=VWy0K4iYsoHJ&sslmode=require";
        String username = "tienthanh170803";
        String password = "VWy0K4iYsoHJ";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException  | SQLException e) {
            throw new SQLException("Error connecting to database", e);
        }
        return connection;
    }
}
