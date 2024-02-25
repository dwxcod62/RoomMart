package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
    private static final String GET_NOTIFICATION_BY_OWNER_ID =
            "SELECT notification_id, title, content, create_date, hostel_id \n" +
                    "FROM Notifications WHERE hostel_owner_account_id = ?";
    private static final String GET_NOTIFICATION_BY_RENTER_ID =
            "SELECT Notifications.title, Notifications.content, Notifications.create_date\n" +
                    "FROM Accounts\n" +
                    "INNER JOIN Contracts ON Accounts.account_id = Contracts.renter_id\n" +
                    "INNER JOIN Rooms ON Contracts.room_id = Rooms.room_id\n" +
                    "INNER JOIN Hostels ON Rooms.hostel_id = Hostels.hostel_id\n" +
                    "INNER JOIN Notifications ON Hostels.hostel_id = Notifications.hostel_id\n" +
                    "WHERE Accounts.account_id = ?\n" +
                    "ORDER BY create_date DESC";
    public List<Notification> getNotificationByRenterId(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        List<Notification> noti = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_NOTIFICATION_BY_RENTER_ID);
                pst.setInt(1, accId);
                ResultSet rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createDate = rs.getString("create_date");
                    noti.add(Notification
                            .builder()
                            .title(title)
                            .content(content)
                            .createDate(createDate)
                            .build());
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
        return noti;
    }

    public List<Notification> getNotificationByOwnerId(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        List<Notification> noti = new ArrayList<>();
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_NOTIFICATION_BY_OWNER_ID);
                pst.setInt(1, accId);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int notiId = rs.getInt("notification_id");
                    String title = rs.getString("title");
                    int hostel_id = rs.getInt("hostel_id");
                    String content = rs.getString("content");
                    String createDate = rs.getString("create_date");
                    noti.add(Notification
                            .builder()
                            .notification_id(notiId)
                            .hostel_id(hostel_id)
                            .title(title)
                            .content(content)
                            .createDate(createDate)
                            .build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return noti;
    }
}
