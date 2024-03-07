package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao {
    private static final String GET_REPORTS_BY_ID =
            "SELECT * FROM Reports\n" +
                    "WHERE send_account_id = ?\n" +
                    "ORDER BY send_date DESC";
    private static final String INSERT_REPORT =
            "INSERT INTO Reports (send_date, content, status, " +
                    "reply_account_id, send_account_id, cate_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?);";

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
        Connection cn = null;
        int reportId = -1;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(INSERT_REPORT, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, report.getSendDate());
                ptm.setString(2, report.getContent());
                ptm.setInt(3, report.getStatus());
                ptm.setInt(4, report.getReplyAccountID());
                ptm.setInt(5, report.getSendAccountID());
                ptm.setInt(6, report.getCateID());

                boolean check = ptm.executeUpdate() > 0;

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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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


    public Report getReportById(int reportId) throws SQLException {
        Report report = new Report();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String GET_REPORT =
                        "SELECT * FROM  Reports WHERE id_report = ?";
                pst = cn.prepareStatement(GET_REPORT);
                pst.setInt(1, reportId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int reportID = rs.getInt("id_report");
                    String sendDate = rs.getString("send_date");
                    String content = rs.getString("content");
                    int status = rs.getInt("status");
                    String reply = rs.getString("reply");
                    String completeDate = rs.getString("complete_date");
                    int replyAccountID = rs.getInt("reply_account_id");
                    int sendAccountID = rs.getInt("send_account_id");
                    int cateID = rs.getInt("cate_id");
                    report = Report.builder()
                            .reportID(reportID)
                            .sendDate(sendDate)
                            .content(content)
                            .status(status)
                            .reply(reply)
                            .completeDate(completeDate)
                            .replyAccountID(replyAccountID)
                            .sendAccountID(sendAccountID)
                            .cateID(cateID)
                            .build();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return report;
    }

    public boolean updateReportToProcess(int reportId, String replyMsg) throws SQLException {
        boolean check = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                String UPDATE_REPORT_TO_PROCESS = "UPDATE Reports SET status = 1, reply_date = GETDATE(), reply = ? WHERE id_report = ?";
                ptm = cn.prepareStatement(UPDATE_REPORT_TO_PROCESS);
                ptm.setString(1, replyMsg);
                ptm.setInt(2, reportId);
                check = ptm.executeUpdate() > 0;

                if (!check) {
                    cn.rollback();
                } else {
                    cn.commit();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, null);
        }
        return check;
    }

    public boolean updateReportToFinished(int reportId) throws SQLException {
        boolean check = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                String UPDATE_REPORT_TO_FINISHED = "UPDATE Reports SET status = 2, complete_date = GETDATE() WHERE id_report = ?";
                ptm = cn.prepareStatement(UPDATE_REPORT_TO_FINISHED);
                ptm.setInt(1, reportId);
                check = ptm.executeUpdate() > 0;

                if (!check) {
                    cn.rollback();
                } else {
                    cn.commit();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, null);
        }
        return check;
    }

    public ArrayList<Report> getListReportByHostelId(int hostelId) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Report> listReport = new ArrayList<>();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                String sql = "select A.id_report, A.send_date, A.content, A.status, A.reply, A.reply_date, A.complete_date, A.reply_account_id, A.send_account_id, A.cate_id\n" +
                        "from [dbo].[Reports] A join [dbo].[Accounts] B on A.send_account_id = B.account_id join [dbo].[Rooms] C on B.room_id = C.room_id join [dbo].[Hostels] D on C.hostel_id = D.hostel_id\n" +
                        "where D.hostel_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int reportID = rs.getInt("id_report");
                    String sendDate = rs.getString("send_date");
                    String content = rs.getString("content");
                    int status = rs.getInt("status");
                    String reply = rs.getString("reply");
                    String replyDate = rs.getString("reply_date");
                    String completeDate = rs.getString("complete_date");
                    int replyAccountID = rs.getInt("reply_account_id");
                    int sendAccountID = rs.getInt("send_account_id");
                    int cateID = rs.getInt("cate_id");
                    listReport.add(Report.builder()
                            .reportID(reportID)
                            .sendDate(sendDate)
                            .content(content)
                            .status(status)
                            .reply(reply)
                            .replyDate(replyDate)
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
            OwnerUtils.closeSQL(conn, pst, rs);
        }
        return listReport;
    }


}
