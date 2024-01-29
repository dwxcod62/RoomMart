package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IRoomDAO;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.RoomInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    @Override
    public List<Room> getListRoomsByHostelOwnerId(int hostelOwnerID) {
        return null;
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
        }
        return number;
    }

    @Override
    public Room getRoomInformationByRoomId(int roomID, int hostelID, int accountOwnerID) {
        return null;
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
