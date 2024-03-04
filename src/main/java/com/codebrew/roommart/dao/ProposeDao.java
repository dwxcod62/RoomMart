package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.Propose;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProposeDao {

    //Admin Dashboard
    private static final String GET_ALL_PROPOSES =
            "SELECT id, content, send_date, reply, reply_date, status, send_account_id,\n" +
                    "reply_account_id FROM Propose ORDER BY send_date DESC";
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
}
