package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IHostelDAO {
    public Hostel getHostelById(int hostelId);
    public Hostel getHostelByRoomId(int roomId);
    public Hostel getHostelByIdWithConstraint(int hostelId, int ownerAccountID) throws SQLException;
    public List<Hostel> getHostelByOwnerId(int hostelOwnerAccountID);
//    public int addHostel(Hostel hostel, List<HostelService> hostelServices);
    public boolean updateHostel(Hostel hostel, int hostelID);
    public Hostel getHostelByRenterId(int renterId);
    public ArrayList<Hostel> getListHostel();
    public ArrayList<Integer> getListRenterIdByHostelId(int hostelId);

    public int addHostel(Hostel hostel, List<HostelService> hostelServices);

    public void addNewHostel(Hostel hostel);
}
