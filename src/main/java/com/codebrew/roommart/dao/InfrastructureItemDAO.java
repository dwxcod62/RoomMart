package com.codebrew.roommart.dao;
import com.codebrew.roommart.dto.InfrastructureItem;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class InfrastructureItemDAO {
    private static final String GET_INFRASTRUCTURE_BY_NAME =
            "SELECT id_infrastructure_item, infrastructure_name FROM InfrastructureItem WHERE infrastructure_name = ?";

    public InfrastructureItem getInfrastructureByName(String infrastructureName) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        InfrastructureItem infrastructureItem = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_INFRASTRUCTURE_BY_NAME);
                pst.setString(1, infrastructureName);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    infrastructureItem = InfrastructureItem.builder().build();
                }
            }
        } catch (Exception e) {
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
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return infrastructureItem;
    }

}
