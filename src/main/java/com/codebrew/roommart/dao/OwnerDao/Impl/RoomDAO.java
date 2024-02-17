package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO implements IRoomDAO {
    @Override
    public List<Room> getListRoomsByHostelId(int hostelID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "SELECT room_id, hostel_id, room_number, capacity, room_area, has_attic, room_status\n" +
                        "FROM Rooms\n" +
                        "WHERE hostel_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, hostelID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int roomNumber = rs.getInt("room_number");
                        int capacity = rs.getInt("capacity");
                        double roomArea = rs.getDouble("room_area");
                        int hasAttic = rs.getInt("has_attic");
                        int roomStatus = rs.getInt("room_status");
                        RoomInformation roomInformation = null;
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(hostelID)
                                .roomNumber(roomNumber)
                                .roomArea(roomArea)
                                .capacity(capacity)
                                .roomStatus(roomStatus)
                                .hasAttic(hasAttic)
                                .roomInformation(roomInformation)
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return rooms;
    }

    @Override
    public List<Room> getListRoomsByHostelOwnerId(int hostelOwnerID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                // select room theo ownerId
                String sql = "SELECT room_id, Hostels.hostel_id as 'hostel_id', room_number, room_status\n" +
                        "FROM Hostels, Rooms\n" +
                        "WHERE Hostels.owner_account_id = ?\n" +
                        "AND Hostels.hostel_id = Rooms.hostel_id\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, hostelOwnerID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int roomID = rs.getInt("room_id");
                        int hostelId = rs.getInt("hostel_id");
                        int roomNumber = rs.getInt("room_number");
                        int roomStatus = rs.getInt("room_status");
                        rooms.add(Room.builder()
                                .roomId(roomID)
                                .hostelId(hostelId)
                                .roomNumber(roomNumber)
                                .roomStatus(roomStatus)
                                .build());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return rooms;
    }

    @Override
    public int getNumberRoomSpecificHostel(int hostelID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int number = 0;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                // Insert new room include Nha ve sinh, cua so, cua ra vao, may lanh theo thứ tự
                String sql = "SELECT COUNT(room_id) as quantity\n" +
                        "FROM Rooms\n" +
                        "WHERE hostel_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, hostelID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    number = rs.getInt("quantity");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return number;
    }

    @Override
    public Room getRoomInformationByRoomId(int roomID, int hostelID, int accountOwnerID) {
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
                pst.setInt(2, hostelID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int hostelId = rs.getInt("hostel_id");
                    int roomNumber = rs.getInt("room_number");
                    int capacity = rs.getInt("capacity");
                    int roomStatus = rs.getInt("room_status");
                    double roomArea = rs.getDouble("room_area");
                    int hasAttic = rs.getInt("has_attic");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    RoomInformation roomInformation = RoomInformation.builder()
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                    room = Room.builder()
                            .roomId(roomID)
                            .hostelId(hostelId)
                            .roomNumber(roomNumber)
                            .roomStatus(roomStatus)
                            .capacity(capacity)
                            .roomArea(roomArea)
                            .hasAttic(hasAttic)
                            .roomInformation(roomInformation)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return room;
    }

    @Override
    public boolean updateRoom(int roomID, int roomNumber, int capacity, double roomArea, int hasAttic) {
        return false;
    }

    @Override
    public boolean updateRoomStatus(int roomID, int status) {
        return false;
    }

    @Override
    public Room getRoomInfoByRenterId(int renterId) {
        return null;
    }

    @Override
    public Room getRoomById(int roomId) {
        return null;
    }
}
