package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.InfrastructureItem;
import com.codebrew.roommart.dto.Infrastructures;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InfrastructureDAO {
    public List<InfrastructureItem> getAllInfrastructure() {
        System.out.println("getAllInfrastructure");
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

    public List<Infrastructures> getRoomInfrastructures(int roomID) {
        System.out.println("-> getRoomInfrastructures");
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Infrastructures> infrastructures = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql =
                        "SELECT IR.id_infrastructure, IR.quantity, IR.status, I.infrastructure_name\n" +
                                "FROM public.InfrastructuresRoom IR\n" +
                                "JOIN public.InfrastructureItem I ON IR.id_infrastructure_item = I.id_infrastructure_item\n" +
                                "WHERE IR.room_id = ?";
//                System.out.println(sql);
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
//            System.out.println("infreature: "+infrastructures);
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
        return infrastructures;
    }
}
