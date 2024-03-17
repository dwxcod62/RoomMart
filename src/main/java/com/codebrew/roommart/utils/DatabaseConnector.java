
package com.codebrew.roommart.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {
    public static Connection makeConnection() throws Exception {
        Connection cn = null;
        String IP = "localhost";
        String instanceName=""; // Change here
        String uid = "sa1"; // Change here
        String pwd="1234"; // Change here
        String port = "1433";
        String db = "RoomMart";
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url = "jdbc:sqlserver://" + IP + "\\" + instanceName + ":" + port
                + ";databasename=" + db + ";user=" + uid + ";password=" + pwd + ";encrypt=true;trustServerCertificate=true;";
        Class.forName(driver);
        cn = DriverManager.getConnection(url);
        return cn;
    }
}
