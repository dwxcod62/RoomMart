package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao {
    private static final String GET_NOTIFICATION_BY_OWNER_ID =
            "SELECT notification_id, title, content, create_date, hostel_id \n" +
                    "FROM Notifications WHERE hostel_owner_account_id = ?";

    public List<Notification> getNotificationByOwnerId(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        List<Notification> noti = new ArrayList<>();
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                System.out.println(accId);
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

    public Notification getNotificationById(int notiId) {
        Connection cn = null;
        PreparedStatement pst = null;
        Notification noti = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String GET_NOTIFICATION_BY_ID =
                        "SELECT notification_id, title, content, create_date, hostel_id\n" +
                                "FROM Notifications\n" +
                                "WHERE notification_id = ?";
                pst = cn.prepareStatement(GET_NOTIFICATION_BY_ID);
                pst.setInt(1, notiId);
                ResultSet rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    notiId = rs.getInt("notification_id");
                    int hostelId = rs.getInt("hostel_id");
                    String title = rs.getString("title");
                    String content = rs.getString("content");
                    String createDate = rs.getString("create_date");
                    noti = Notification
                            .builder()
                            .notification_id(notiId)
                            .hostel_id(hostelId)
                            .title(title)
                            .content(content)
                            .createDate(createDate)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, null);
        }
        return noti;
    }
}
