package com.codebrew.roommart.dao;

import com.codebrew.roommart.dao.OwnerDao.Impl.AccountDAO;
import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Propose;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProposeDao {
    //Users Send Propose
    private static final String INSERT_NEW_PROPOSE =
            "INSERT INTO Propose(content, send_date, status, send_account_id)\n" +
                    "VALUES(?, GETDATE(), 0, ?)";

    //Admin Dashboard
    private static final String GET_ALL_PROPOSES =
            "SELECT id, content, send_date, reply, reply_date, status, send_account_id,\n" +
                    "reply_account_id FROM Propose ORDER BY send_date DESC";
    private static final String UPDATE_PROPOSE =
            "UPDATE Propose SET reply = ?, reply_date = GETDATE(), \n" +
                    "status = ?, reply_account_id = ? WHERE id = ?";
    public List<Propose> getAllPropose() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Propose> proposeList = new ArrayList<>();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                pst = conn.prepareStatement(GET_ALL_PROPOSES);
                rs = pst.executeQuery();

                AccountDao accountDAO = new AccountDao();
                while (rs.next()) {
                    Account sendAccount = accountDAO.getAccountById(rs.getInt("send_account_id"));
                    Account replyAccount = accountDAO.getAccountById(rs.getInt("reply_account_id"));
                    proposeList.add(Propose.builder()
                            .id(rs.getInt("id"))
                            .content(rs.getString("content"))
                            .sendDate(rs.getString("send_date"))
                            .reply(rs.getString("reply"))
                            .replyDate(rs.getString("reply_date"))
                            .status(rs.getInt("status"))
                            .sendAccount(sendAccount)
                            .replyAccount(replyAccount).build());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return proposeList;
    }

    // Admin Manage Propose _ Handle

    public boolean updatePropose(int proposeId, String replyContent, int status, int replyAccountId) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                pst = conn.prepareStatement(UPDATE_PROPOSE);
                pst.setString(1, replyContent);
                pst.setInt(2, status);
                pst.setInt(3, replyAccountId);
                pst.setInt(4, proposeId);
                check = pst.executeUpdate() > 0;
                if (!check) {
                    conn.rollback();
                }
                conn.setAutoCommit(true);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return check;
    }

public boolean insertNewPropose(Propose propose) {
        Connection conn = null;
        PreparedStatement pst = null;
        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                conn.setAutoCommit(false);
                pst = conn.prepareStatement(INSERT_NEW_PROPOSE);
                pst.setString(1, propose.getContent());
                pst.setInt(2, propose.getSendAccount().getAccId());
                check = pst.executeUpdate() > 0;
                if (!check) {
                    conn.rollback();
                }
                conn.setAutoCommit(true);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return check;
    }

    public List<Propose> getAllProposeBySenderId(int senderId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Propose> proposeList = new ArrayList<>();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                String GET_ALL_PROPOSES_BY_SENDER_ID =
                        "SELECT id, content, send_date, reply, reply_date, status, send_account_id,\n" +
                                "reply_account_id FROM Propose WHERE send_account_id = ? ORDER BY send_date DESC";
                pst = conn.prepareStatement(GET_ALL_PROPOSES_BY_SENDER_ID);
                pst.setInt(1, senderId);
                rs = pst.executeQuery();

                AccountDAO accountDAO = new AccountDAO();
                while (rs.next()) {
                    Account sendAccount = accountDAO.getAccountById(rs.getInt("send_account_id"));
                    Account replyAccount = accountDAO.getAccountById(rs.getInt("reply_account_id"));
                    proposeList.add(Propose.builder()
                            .id(rs.getInt("id"))
                            .content(rs.getString("content"))
                            .sendDate(rs.getString("send_date"))
                            .reply(rs.getString("reply"))
                            .replyDate(rs.getString("reply_date"))
                            .status(rs.getInt("status"))
                            .sendAccount(sendAccount)
                            .replyAccount(replyAccount).build());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, pst, rs);
        }
        return proposeList;
    }

}
