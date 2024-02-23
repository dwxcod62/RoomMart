package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private static final String GET_REPORTS_BY_ID = "SELECT * FROM Reports where send_account_id = ?";
    private static final String INSERT_REPORT =
            "INSERT INTO public.reports(send_date, content, status, reply_account_id, send_account_id, cate_id) VALUES(?, ?, ?, ?, ?, ?)";

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

    public int addReport(Report report) throws SQLException {
        boolean check = false;
        Connection cn = null;
        int reportId = -1;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            //Insert table Hostel
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, report.getSendDate());
                ptm.setString(2, report.getContent());
                ptm.setInt(3, report.getStatus());
                ptm.setInt(4, report.getReplyAccountID());
                ptm.setInt(5, report.getSendAccountID());
                ptm.setInt(6, report.getCateID());
                check = ptm.executeUpdate() > 0;

                if (!check) {
                    cn.rollback();
                } else {

                    rs = ptm.getGeneratedKeys();

                    if (rs.next()) {
                        reportId = rs.getInt(1);
                    }
                    cn.commit();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return reportId;
    }
}
