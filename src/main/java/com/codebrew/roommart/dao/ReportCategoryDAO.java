package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.ReportCategory;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportCategoryDAO {
    private static final String GET_REPORT_CATEGORY = "SELECT * FROM ReportCategory";
    public List<ReportCategory> getReportCategory() throws SQLException {
        List<ReportCategory> reportCategories = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                st = cn.createStatement();
                rs = st.executeQuery(GET_REPORT_CATEGORY);
                while (rs != null && rs.next()) {
                    int cateID = rs.getInt("cate_id");
                    String cateTitle = rs.getString("title");
                    reportCategories.add(new ReportCategory(cateID, cateTitle));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return reportCategories;
    }

    public ReportCategory getReportCategoryById(int cateId) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ReportCategory reportCategory = new ReportCategory();
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                String GET_REPORT_CATEGORY_BY_ID = "SELECT  cate_id, title FROM ReportCategory WHERE cate_id = ?";
                pst = conn.prepareStatement(GET_REPORT_CATEGORY_BY_ID);
                pst.setInt(1, cateId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    int cateID = rs.getInt("cate_id");
                    String cateTitle = rs.getString("title");
                    reportCategory = new ReportCategory(cateID, cateTitle);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, pst, rs);
        }
        return reportCategory;
    }
}
