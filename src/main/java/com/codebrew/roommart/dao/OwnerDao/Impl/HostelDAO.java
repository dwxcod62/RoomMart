package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IHostelDAO;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.dto.OwnerDTO.Hostel;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HostelDAO implements IHostelDAO {
    private static final String GET_HOSTEL_BY_OWNER_ID =
            "SELECT hostel_id, owner_account_id, name, address, ward, district, city FROM Hostels WHERE owner_account_id = ? and hostels.status=0";
    private static final String GET_HOSTEL_BY_ID_WITH_CONSTRAINT =
            "SELECT hostel_id, owner_account_id, name, address, ward, district, city FROM Hostels WHERE hostel_id = ? AND owner_account_id = ? and hostels.status=0";

    private static final String INSERT_HOSTEl =
            "INSERT INTO Hostels(owner_account_id, name, address, ward, district, city,status) VALUES(?, ?, ?, ?, ?, ?,?)";

    private static final String INSERT_NEW_HOSTEl =
            "INSERT INTO Hostels(owner_account_id, name, address, ward, district, city,status) VALUES(?, ?, ?, ?, ?, ?,1)";

    private static final String INSERT_HOSTEL_SERVICE =
            "INSERT INTO HostelService (hostel_id, service_id, service_price, valid_date, status)\n" +
                    "VALUES (?, ?, ?, GETDATE(), 1)";
    private static final String INSERT_HOSTEL_IMG =
            "INSERT INTO imgHostel (hostel_id, imgURL)\n" +
                    "VALUES (?,?)";
    @Override
    public Hostel getHostelById(int hostelId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String GET_HOSTEL_BY_ID =
                        "SELECT hostel_id, owner_account_id, name, address, ward, district, city FROM Hostels WHERE hostel_id = ?";

                pst = cn.prepareStatement(GET_HOSTEL_BY_ID);
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int hostelOwnerAccountID = rs.getInt("owner_account_id");
                    String name =  rs.getString("name");
                    String address =  rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = Hostel.builder()
                            .hostelID(hostelId)
                            .hostelOwnerAccountID(hostelOwnerAccountID)
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return hostel;
    }

    @Override
    public Hostel getHostelByIdWithConstraint(int hostelId, int ownerAccountID) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_BY_ID_WITH_CONSTRAINT);
                pst.setInt(1, hostelId);
                pst.setInt(2, ownerAccountID);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int hostelOwnerAccountID = rs.getInt("owner_account_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = Hostel.builder()
                            .hostelID(hostelId)
                            .hostelOwnerAccountID(hostelOwnerAccountID)
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return hostel;
    }

    @Override
    public ArrayList<Integer> getListRenterIdByHostelId(int hostelId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Integer> accIdList = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("SELECT A.[account_id] AS account_id\n" +
                        "FROM [dbo].[Accounts] AS A JOIN [dbo].[Rooms] AS R ON A.[room_id] = R.[room_id]\n" +
                        "WHERE R.[hostel_id] = ? AND A.[status] = 1");
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int accId = rs.getInt("account_id");
                    accIdList.add(accId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (cn != null) {
                    cn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return accIdList;
    }

    @Override
    public boolean checkOwnerHostel(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT [owner_account_id]\n" +
                        "FROM [dbo].[Hostels]\n" +
                        "WHERE [owner_account_id] = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, accId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return result;
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
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return listHostels;
    }

    @Override
    public int addHostel(Hostel hostel, List<HostelService> hostelServices) {
        int id = -1;
        boolean check;
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            //Insert table Hostel
            if (cn != null) {
                cn.setAutoCommit(false);

                ptm = cn.prepareStatement(INSERT_HOSTEl, Statement.RETURN_GENERATED_KEYS);
                ptm.setInt(1, hostel.getHostelOwnerAccountID());
                ptm.setString(2, hostel.getHostelName());
                ptm.setString(3, hostel.getAddress());
                ptm.setString(4, hostel.getWard());
                ptm.setString(5, hostel.getDistrict());
                ptm.setString(6, hostel.getCity());
                ptm.setInt(7, hostel.getStatus());
                check = ptm.executeUpdate() > 0;
                if (!check) {
                    cn.rollback();
                    cn.setAutoCommit(true);
                    return -1;
                }

                rs = ptm.getGeneratedKeys();

                if (rs.next()) {
                    id = rs.getInt(1);
                }

                for (HostelService ser : hostelServices) {
                    ptm = cn.prepareStatement(INSERT_HOSTEL_SERVICE);
                    ptm.setInt(1, id);
                    ptm.setInt(2, ser.getServiceID());
                    ptm.setInt(3, ser.getServicePrice());
                    check = ptm.executeUpdate() > 0;
                    if (!check) {
                        cn.rollback();
                        cn.setAutoCommit(true);
                        return -1;
                    }
                }
                for(String url : hostel.getImgUrl()){
                    ptm = cn.prepareStatement(INSERT_HOSTEL_IMG);
                    ptm.setInt(1, id);
                    ptm.setString(2, url);
                    check = ptm.executeUpdate() > 0;
                    if (!check) {
                        cn.rollback();
                        cn.setAutoCommit(true);
                        return -1;
                    }
                }
                cn.commit();
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, rs);
        }
        return id;
    }

    @Override
    public void addNewHostel(Hostel hostel) {
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            //Insert table Hostel
            if (cn != null) {
                ptm = cn.prepareStatement(INSERT_NEW_HOSTEl);

                ptm.setInt(1, hostel.getHostelOwnerAccountID());
                ptm.setString(2, hostel.getHostelName());
                ptm.setString(3, hostel.getAddress());
                ptm.setString(4, hostel.getWard());
                ptm.setString(5, hostel.getDistrict());
                ptm.setString(6, hostel.getCity());
                ptm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, rs);
        }
    }

    @Override
    public boolean checkOwnerRoom(int accId, int roomId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT R.room_id FROM Rooms AS R JOIN Hostels AS H ON R.hostel_id = H.hostel_id\n" +
                        "WHERE R.room_id = ? AND H.owner_account_id = ?";
                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomId);
                pst.setInt(2, accId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    result = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return result;
    }

    @Override
    public Hostel getHostelByRoomId(int roomId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostel = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String GET_HOSTEL_BY_ROOM_ID =
                        "SELECT H.hostel_id, owner_account_id, name, address, ward, district, city\n" +
                                "FROM Hostels H JOIN Rooms R ON H.hostel_id = R.hostel_id WHERE R.room_id = ?";
                pst = cn.prepareStatement(GET_HOSTEL_BY_ROOM_ID);
                pst.setInt(1, roomId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int hostelOwnerAccountID = rs.getInt("owner_account_id");
                    int hostelId = rs.getInt("hostel_id");
                    String name =  rs.getString("name");
                    String address =  rs.getString("address");
                    String ward = rs.getString("ward");
                    String district = rs.getString("district");
                    String city = rs.getString("city");
                    hostel = Hostel.builder()
                            .hostelID(hostelId)
                            .hostelOwnerAccountID(hostelOwnerAccountID)
                            .hostelName(name)
                            .address(address)
                            .ward(ward)
                            .district(district)
                            .city(city)
                            .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return hostel;
    }

    @Override
    public boolean updateHostel(Hostel hostel, int hostelID) {
        boolean checkUpdate = false;
        Connection cn = null;
        PreparedStatement ptm = null;
        try {
            cn = DatabaseConnector.makeConnection();

            if (cn != null) {
                cn.setAutoCommit(false);
                String UPDATE_HOSTEL =
                        "UPDATE Hostels SET name = ?, address = ?, ward = ?, district = ?, city = ? WHERE hostel_id = ?";
                ptm = cn.prepareStatement(UPDATE_HOSTEL);
                ptm.setString(1, hostel.getHostelName());
                ptm.setString(2, hostel.getAddress());
                ptm.setString(3, hostel.getWard());
                ptm.setString(4, hostel.getDistrict());
                ptm.setString(5, hostel.getCity());
                ptm.setInt(6, hostelID);
                checkUpdate = ptm.executeUpdate() > 0;

                if (!checkUpdate) {
                    cn.rollback();
                } else {
                    cn.commit();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, null);
        }
        return checkUpdate;
    }

    @Override
    public boolean deleteHostelById(int hostelId) {
        boolean checkDelete = false;
        Connection cn = null;
        PreparedStatement ptm = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                ptm = cn.prepareStatement("update hostels set status = -1 where hostel_id = ?");

                ptm.setInt(1, hostelId);
                checkDelete = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, null);
        }
        return checkDelete;
    }
}
