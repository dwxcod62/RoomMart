package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    private static final String GET_REPORTS_BY_ID =
            "SELECT * FROM Reports\n" +
                    "WHERE send_account_id = ?\n" +
                    "ORDER BY send_date DESC";

    public List<Report> getReportByRenterId(int id) throws SQLException {
        List<Report> reports = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_REPORTS_BY_ID);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int reportID = rs.getInt("id_report");
                    String sendDate = rs.getString("send_date");
                    String content = rs.getString("content");
                    int status = rs.getInt("status");
                    String reply = rs.getString("reply");
                    String completeDate = rs.getString("complete_date");
                    int replyAccountID = rs.getInt("reply_account_id");
                    int sendAccountID = rs.getInt("send_account_id");
                    int cateID = rs.getInt("cate_id");
                    reports.add(Report.builder()
                            .reportID(reportID)
                            .sendDate(sendDate)
                            .content(content)
                            .status(status)
                            .reply(reply)
                            .completeDate(completeDate)
                            .replyAccountID(replyAccountID)
                            .sendAccountID(sendAccountID)
                            .cateID(cateID)
                            .build());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return reports;
    }
}
