package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HostelDAO implements IHostelDAO {
    private static final String GET_HOSTEL_BY_OWNER_ID =
            "SELECT hostel_id, owner_account_id, name, address, ward, district, city FROM Hostels WHERE owner_account_id = ?";
    @Override
    public Hostel getHostelById(int hostelId) {
        return null;
    }

    @Override
    public Hostel getHostelByRoomId(int roomId) {
        return null;
    }

    @Override
    public Hostel getHostelByIdWithConstraint(int hostelId, int ownerAccountID) {
        return null;
    }

    @Override
    public List<Hostel> getHostelByOwnerId(int hostelOwnerAccountID) {
        List<Hostel> listHostels = new ArrayList<>();
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_BY_OWNER_ID);
                pst.setInt(1, hostelOwnerAccountID);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int hostelID = rs.getInt("hostel_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = Hostel.builder()
                            .hostelID(hostelID)
                            .hostelOwnerAccountID(hostelOwnerAccountID)
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city).build();
                    listHostels.add(hostel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHostels;
    }

    @Override
    public boolean updateHostel(Hostel hostel, int hostelID) {
        return false;
    }

    @Override
    public Hostel getHostelByRenterId(int renterId) {
        return null;
    }

    @Override
    public ArrayList<Hostel> getListHostel() {
        return null;
    }

    @Override
    public ArrayList<Integer> getListRenterIdByHostelId(int hostelId) {
        return null;
    }
}
