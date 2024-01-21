package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.InfrastructureItem;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureDAO {
    public List<InfrastructureItem> getAllInfrastructure() {
        Connection cn = null;
        Statement pst = null;
        ResultSet rs = null;
        ArrayList<InfrastructureItem> infrastructureItems = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT id_infrastructure_item, infrastructure_name\n" +
                        "FROM InfrastructureItem\n";

                pst = cn.createStatement();

                rs = pst.executeQuery(sql);
                if (rs != null) {
                    while (rs.next()) {
                        int idInfrastructureItem = rs.getInt("id_infrastructure_item");
                        String infrastructureName = rs.getString("infrastructure_name");
                        infrastructureItems.add(new InfrastructureItem(idInfrastructureItem, infrastructureName));
                    }
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

        return infrastructureItems;
    }
}
