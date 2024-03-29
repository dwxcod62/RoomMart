package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IHostelDAO {
    public Hostel getHostelById(int hostelId);
    public Hostel getHostelByIdWithConstraint(int hostelId, int ownerAccountID) throws SQLException;
    public List<Hostel> getHostelByOwnerId(int hostelOwnerAccountID);

    public int addHostel(Hostel hostel, List<HostelService> hostelServices);

    public void addNewHostel(Hostel hostel);

    public boolean checkOwnerRoom(int accId, int roomId);

    public Hostel getHostelByRoomId(int roomId);

    public boolean updateHostel(Hostel hostel, int hostelID);

    boolean deleteHostelById(int hostelId);

    public boolean checkOwnerHostel(int accId);

    public ArrayList<Integer> getListRenterIdByHostelId(int hostelId);
}
