package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Room;

import java.util.List;

public interface IRoomDAO {
    public List<Room> getListRoomsByHostelId(int hostelID);
    public List<Room> getListRoomsByHostelOwnerId(int hostelOwnerID);
    public int getNumberRoomSpecificHostel(int hostelID);

    public Room getRoomInformationByRoomId(int roomID, int hostelID, int accountOwnerID);
    public boolean updateRoom(int roomID, int roomNumber, int capacity, double roomArea, int hasAttic);
    public boolean updateRoomStatus(int roomID, int status);
    public Room getRoomInfoByRenterId(int renterId);
    public Room getRoomById(int roomId);
    public Room checkRoomOwner(int owner_email, int room_id);
}
