package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IConsumeDAO;
import com.codebrew.roommart.dto.OwnerDTO.Consume;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumeDAO implements IConsumeDAO {
    @Override
    public List<Consume> getConsumeHistory(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Consume> consumesList = new ArrayList();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT consume_id, number_electric, number_water, update_date, status\n" +
                        "FROM Consumes\n" +
                        "WHERE room_id = ?\n" +
                        "ORDER BY consume_id DESC";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int consumeID = rs.getInt("consume_id");
                        int numberElectric = rs.getInt("number_electric");
                        int numberWater = rs.getInt("number_water");
                        String updateDate = rs.getString("update_date");
                        int status = rs.getInt("status");
                        consumesList.add(Consume.builder()
                                .consumeID(consumeID)
                                .roomID(roomID)
                                .numberElectric(numberElectric)
                                .numberWater(numberWater)
                                .updateDate(updateDate)
                                .status(status).build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return consumesList;
    }

    @Override
    public Consume getNearestConsume(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Consume consume = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT consume_id, number_electric, number_water, update_date, status \n" +
                        "FROM Consumes\n" +
                        "WHERE room_id = ?\n" +
                        "ORDER BY update_date DESC  LIMIT 1";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int consumeID = rs.getInt("consume_id");
                    int numberElectric = rs.getInt("number_electric");
                    int numberWater = rs.getInt("number_water");
                    String updateConsumeDate = rs.getString("update_date");
                    int status = rs.getInt("status");
                    consume = Consume.builder()
                            .consumeID(consumeID)
                            .roomID(roomID)
                            .numberElectric(numberElectric)
                            .numberWater(numberWater)
                            .updateDate(updateConsumeDate)
                            .status(status).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return consume;
    }

    @Override
    public List<Consume> getConsumeThisMonth(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Consume> consumesList = new ArrayList();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT consume_id, number_electric, number_water, update_date, status\n" +
                        "FROM Consumes\n" +
                        "WHERE room_id = ?\n AND status = 0\n" +
                        "ORDER BY update_date DESC";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int consumeID = rs.getInt("consume_id");
                        int numberElectric = rs.getInt("number_electric");
                        int numberWater = rs.getInt("number_water");
                        String updateDate = rs.getString("update_date");
                        int status = rs.getInt("status");
                        consumesList.add(Consume.builder()
                                .consumeID(consumeID)
                                .roomID(roomID)
                                .numberElectric(numberElectric)
                                .numberWater(numberWater)
                                .updateDate(updateDate)
                                .status(status).build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return consumesList;
    }

    @Override
    public Consume getConsumeByID(int consumeID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Consume consume = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT number_electric, number_water, update_date, status, room_id\n" +
                        "FROM Consumes\n" +
                        "WHERE consume_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, consumeID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int numberElectric = rs.getInt("number_electric");
                    int numberWater = rs.getInt("number_water");
                    String updateConsumeDate = rs.getString("update_date");
                    int status = rs.getInt("status");
                    int roomID = rs.getInt("room_id");
                    consume = Consume.builder()
                            .consumeID(consumeID)
                            .roomID(roomID)
                            .numberElectric(numberElectric)
                            .numberWater(numberWater)
                            .updateDate(updateConsumeDate)
                            .status(status).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return consume;
    }
}