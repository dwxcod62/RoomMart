package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Notification;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao {
    private static final String GET_NOTIFICATION_BY_OWNER_ID =
            "SELECT notification_id, title, content, create_date, hostel_id \n" +
                    "FROM Notifications WHERE hostel_owner_account_id = ?";
    private static final String GET_NOTIFICATION_BY_RENTER_ID =
            "SELECT n.title, n.content, n.create_date\n" +
                    "FROM Notifications n\n" +
                    "JOIN Rooms r ON n.hostel_id = r.hostel_id\n" +
                    "JOIN Contracts c ON r.room_id = c.room_id\n" +
                    "JOIN Accounts a ON a.account_id = c.renter_id\n" +
                    "WHERE a.account_id = ?";

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

    public int creatNotification(int ownerId, int hostelId, String title, String content){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int notiId = -1;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                // Stop auto commit for rollback if transaction insert data have any problem
                cn.setAutoCommit(false);

                String INSERT_NOTIFICATION = "INSERT INTO [dbo].[Notifications] VALUES(?, ?, ?, ?, GETDATE())";

                // Add into Accounts table
                pst = cn.prepareStatement(INSERT_NOTIFICATION, Statement.RETURN_GENERATED_KEYS); //nhận lại các khóa được tạo tự động
                // Return key Identity of data just inserted
                pst.setInt(1, ownerId);
                pst.setInt(2, hostelId);
                pst.setString(3, title);
                pst.setString(4,content);

                if (pst.executeUpdate() > 0) {
                    rs = pst.getGeneratedKeys();
                    if (rs.next()) {
                        notiId = rs.getInt(1); //  được thiết lập bằng ID được sinh tự động
                    }
                } else {
                    cn.rollback();
                }
                cn.setAutoCommit(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return notiId;
    }
}
