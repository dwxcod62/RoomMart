package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInfomation;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    public List<Room> getListRoomsByHostelId(int propID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "SELECT room_id, property_id, room_number, room_area, attic, room_status\n" +
                        "FROM Room\n" +
                        "WHERE property_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, propID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int roomNumber = rs.getInt("room_number");
//                        int capacity = rs.getInt("capacity");
                        int roomArea = rs.getInt("room_area");
                        int attic = rs.getInt("attic");
                        int roomStatus = rs.getInt("room_status");
                        RoomInfomation roomInformation = null;
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .propertyId(propID)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .roomStatus(roomStatus)
                                .attic(attic)
                                .roomInfomation(roomInformation)
                                .build());
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
        return rooms;
    }
    public Room getRoomInformationByRoomId(int roomID, int propID, int accountOwnerID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room room = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, H.hostel_id as 'hostel_id', room_number, capacity, room_status, room_area, has_attic, name, address, ward, district, city\n" +
                        "FROM Rooms R JOIN Hostels H ON R.hostel_id = H.hostel_id\n" +
                        "WHERE R.room_id = ?\n" +
                        "AND H.hostel_id = ?\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                pst.setInt(2, propID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int propertyId = rs.getInt("propertyId");
                    int roomNumber = rs.getInt("room_number");
//                    int capacity = rs.getInt("capacity");
                    int roomStatus = rs.getInt("room_status");
                    double roomArea = rs.getDouble("room_area");
                    int hasAttic = rs.getInt("has_attic");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    RoomInfomation roomInformation = RoomInfomation.builder()
                            .property_name(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                    room = Room.builder()
                            .roomId(roomID)
                            .propertyId(propertyId)
                            .roomNumber(roomNumber)
                            .roomStatus(roomStatus)
                            .roomArea(roomArea)
                            .attic(hasAttic)
                            .roomInfomation(roomInformation)
                            .build();
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
        return room;
    }
}
