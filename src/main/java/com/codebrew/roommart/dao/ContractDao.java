package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.Information;
import com.codebrew.roommart.dto.Room;
import com.codebrew.roommart.dto.ServiceInfo;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {
    private static final String
            GET_RENTER_BY_CONTRACT = "SELECT renter_full_name, renter_phone, renter_identify_card, renter_birthday\n" +
            "FROM contract_details\n" +
            "WHERE contract_details_id IN ( " +
            "    SELECT contract_details_id " +
            "    FROM contract_main " +
            "    WHERE renter_id = ? " +
            ")";
    private static final String
            GET_OWNER_BY_CONTRACT = "SELECT cd.owner_full_name, cd.owner_phone, cd.owner_identify_card, cd.owner_birthday\n" +
            "FROM contract_main cm\n" +
            "JOIN contract_details cd ON cm.contract_details_id = cd.contract_details_id\n" +
            "WHERE cm.renter_id = ?";

    private static final String
            GET_HOSTEL_BY_CONTRACT = "SELECT h.name, h.address, h.ward, h.district, h.city " +
            "FROM contract_main cm " +
            "JOIN rooms r ON cm.room_id = r.room_id " +
            "JOIN hostels h ON r.hostel_id = h.hostel_id " +
            "WHERE cm.renter_id = ?";
    private static final String
            GET_ROOM_BY_CONTRACT = "SELECT rooms.room_number, rooms.capacity, rooms.room_area, " +
            "rooms.has_attic, rooms.room_status\n" +
            "FROM contract_main\n" +
            "INNER JOIN rooms ON contract_main.room_id = rooms.room_id\n" +
            "WHERE contract_main.renter_id = ?";
    private static final String
            GET_INFRASTRUCTURES_BY_CONTRACT = "SELECT infrastructureitem.infrastructure_name, infrastructuresroom.quantity\n" +
            "FROM contract_main\n" +
            "JOIN rooms ON contract_main.room_id = rooms.room_id\n" +
            "JOIN infrastructuresroom ON rooms.room_id = infrastructuresroom.room_id\n" +
            "JOIN infrastructureitem ON infrastructuresroom.id_infrastructure_item = infrastructureitem.id_infrastructure_item\n" +
            "WHERE contract_main.renter_id = ?";
    private static final String
            GET_SERVICES_BY_CONTRACT = "SELECT s.service_name, hs.service_price, s.unit\n" +
            "FROM contract_main cm\n" +
            "JOIN rooms r ON cm.room_id = r.room_id\n" +
            "JOIN hostelservice hs ON r.hostel_id = hs.hostel_id\n" +
            "JOIN services s ON hs.service_id = s.service_id\n" +
            "WHERE cm.renter_id = ?";
    private static final String
            GET_INFO_CONTRACT = "SELECT cd.start_date, cd.end_date, cd.deposit, cd.cost_per_month\n" +
            "FROM contract_main cm\n" +
            "JOIN contract_details cd ON cm.contract_details_id = cd.contract_details_id\n" +
            "WHERE cm.renter_id = ?";

    public Hostel getHostelByContract(int renterId){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Hostel hostelInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_HOSTEL_BY_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
            }  if (rs != null && rs.next()) {
                String hostelName = rs.getString("name");
                String address = rs.getString("address");
                String ward = rs.getString("ward");
                String district = rs.getString("district");
                String city = rs.getString("city");

                hostelInfor = Hostel.builder()
                        .hostelName(hostelName)
                        .address(address)
                        .ward(ward)
                        .district(district)
                        .city(city)
                        .build();
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
        return hostelInfor;
    }

    public Room getRoomByContract(int renterId){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Room roomInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_ROOM_BY_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
            }  if (rs != null && rs.next()) {
                int room_number = rs.getInt("room_number");
                int capacity = rs.getInt("capacity");
                int room_area = rs.getInt("room_area");
                int has_attic = rs.getInt("has_attic");
                int room_status = rs.getInt("room_status");

                roomInfor = Room.builder()
                        .roomNumber(room_number)
                        .capacity(capacity)
                        .roomArea(room_area)
                        .hasAttic(has_attic)
                        .roomStatus(room_status)
                        .build();
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
        return roomInfor;
    }

    public Information getOwnerByContractDetails(int renterId){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Information accountInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_OWNER_BY_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
            }  if (rs != null && rs.next()) {
                String fullName = rs.getString("owner_full_name");
                String phone = rs.getString("owner_phone");
                String cccd = rs.getString("owner_identify_card");
                String bod = rs.getDate("owner_birthday").toString();

                accountInfor = Information.builder()
                        .fullname(fullName)
                        .phone(phone)
                        .cccd(cccd)
                        .birthday(bod)
                        .build();
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
        return accountInfor;
    }
    public List<ServiceInfo> getServicesByContract(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ServiceInfo> servicesList = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_SERVICES_BY_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String serviceName = rs.getString("service_name");
                    String unit = rs.getString("unit");
                    int servicePrice = rs.getInt("service_price");

                    ServiceInfo serviceInfo = ServiceInfo.builder()
                            .serviceName(serviceName)
                            .unit(unit)
                            .servicePrice(servicePrice)
                            .build();

                    servicesList.add(serviceInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
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
        return servicesList;
    }
}
