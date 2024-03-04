package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IInfrastructureDAO;
import com.codebrew.roommart.dto.InfrastructureItem;
import com.codebrew.roommart.dto.Infrastructures;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureDAO implements IInfrastructureDAO {
    @Override
    public List<InfrastructureItem> getAllInfrastructure() {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<InfrastructureItem> infrastructureItems = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT id_infrastructure_item, infrastructure_name\n" +
                        "FROM InfrastructureItem\n";

                pst = cn.prepareStatement(sql);

                rs = pst.executeQuery();
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
            OwnerUtils.closeSQL(cn, pst, rs);
        }

        return infrastructureItems;
    }

    @Override
    public List<Infrastructures> getRoomInfrastructures(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Infrastructures> infrastructures = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql =
                        "SELECT id_infrastructure, quantity, status, I.infrastructure_name\n" +
                                "FROM InfrastructuresRoom AS IR JOIN InfrastructureItem AS I ON IR.id_infrastructure_item = I.id_infrastructure_item\n" +
                                "WHERE IR.room_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int id = rs.getInt("id_infrastructure");
                        String name = rs.getString("infrastructure_name");
                        int quantity = rs.getInt("quantity");
                        int status = rs.getInt("status");
                        infrastructures.add(new Infrastructures(id, name, quantity, status));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return infrastructures;
    }
}
