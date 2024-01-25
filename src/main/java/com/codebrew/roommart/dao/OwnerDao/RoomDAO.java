package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RoomDAO implements IRoomDAO{
    @Override
    public List<Room> getListRoomsByHostelId(int hostelID) {
        return null;
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
