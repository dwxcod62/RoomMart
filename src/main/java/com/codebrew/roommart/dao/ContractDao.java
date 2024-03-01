package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {
    private static final String
            GET_RENTER_BY_CONTRACT = "SELECT ai.fullname, ai.birthday, ai.phone, ai.identity_card_number\n" +
            "FROM Contracts c\n" +
            "JOIN Accounts a ON c.renter_id = a.account_id\n" +
            "JOIN AccountInformations ai ON a.account_id = ai.account_id\n" +
            "WHERE c.renter_id = ?";
    private static final String
            GET_OWNER_BY_CONTRACT = "SELECT ai.fullname, ai.phone, ai.identity_card_number, ai.birthday\n" +
            "FROM Contracts c\n" +
            "JOIN Accounts a ON c.hostel_owner_id = a.account_id\n" +
            "JOIN AccountInformations ai ON a.account_id = ai.account_id\n" +
            "WHERE c.renter_id = ?";

    private static final String
            GET_HOSTEL_BY_CONTRACT = "SELECT h.name, h.address, h.ward, h.district, h.city " +
            "FROM Contracts c " +
            "JOIN rooms r ON c.room_id = r.room_id " +
            "JOIN hostels h ON r.hostel_id = h.hostel_id " +
            "WHERE c.renter_id = ?";
    private static final String
            GET_ROOM_BY_CONTRACT = "SELECT rooms.room_number, rooms.capacity, rooms.room_area, " +
            "rooms.has_attic, rooms.room_status\n" +
            "FROM Contracts\n" +
            "INNER JOIN rooms ON Contracts.room_id = rooms.room_id\n" +
            "WHERE Contracts.renter_id = ?";
    private static final String
            GET_INFRASTRUCTURES_BY_CONTRACT = "SELECT infrastructureitem.infrastructure_name, infrastructuresroom.quantity\n" +
            "FROM Contracts\n" +
            "JOIN rooms ON Contracts.room_id = rooms.room_id\n" +
            "JOIN infrastructuresroom ON rooms.room_id = infrastructuresroom.room_id\n" +
            "JOIN infrastructureitem ON infrastructuresroom.id_infrastructure_item = infrastructureitem.id_infrastructure_item\n" +
            "WHERE Contracts.renter_id = ?";
    private static final String
            GET_SERVICES_BY_CONTRACT = "SELECT s.service_name, hs.service_price, s.unit\n" +
            "FROM Contracts c\n" +
            "JOIN Rooms r ON c.room_id = r.room_id\n" +
            "JOIN Hostels h ON r.hostel_id = h.hostel_id\n" +
            "JOIN Hostelservice hs ON h.hostel_id = hs.hostel_id\n" +
            "JOIN Services s ON hs.service_id = s.service_id\n" +
            "WHERE c.renter_id = ?";
    private static final String
            COUNT_MEMBER_BY_CONTRACT = "SELECT COUNT(*)\n" +
            "FROM Contracts\n" +
            "WHERE room_id IN (SELECT room_id FROM Contracts WHERE renter_id = ?)";
    private static final String
            GET_INFO_CONTRACT = "SELECT start_date, expiration, deposit, price\n" +
            "FROM Contracts\n" +
            "WHERE renter_id = ?";

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

    public Information getOwnerByContract(int renterId){
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
                String fullName = rs.getString("fullname");
                String phone = rs.getString("phone");
                String cccd = rs.getString("identity_card_number");
                String bod = rs.getDate("birthday").toString();

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
    public List<Infrastructures> getInfrastructuresByContract(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Infrastructures> infrastructuresList = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_INFRASTRUCTURES_BY_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("infrastructure_name");
                    int quantity = rs.getInt("quantity");

                    Infrastructures infrastructures = Infrastructures.builder()
                            .name(name)
                            .quantity(quantity)
                            .build();

                    infrastructuresList.add(infrastructures);
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
        return infrastructuresList;
    }

    public int countMemberByContract(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(COUNT_MEMBER_BY_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    count = rs.getInt(1);
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
        return count;
    }
    public Information getContractByRenterId(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Information accountInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_BY_CONTRACT);
                pst.setInt(1, renterId);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullName = rs.getString("fullname");
                    String phone = rs.getString("phone");
                    String cccd = rs.getString("identity_card_number");
                    String bod = rs.getDate("birthday").toString();

                    accountInfor = Information.builder()
                            .fullname(fullName)
                            .phone(phone)
                            .cccd(cccd)
                            .birthday(bod)
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
        return accountInfor;
    }
    public Contract getInfoContract(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contractInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_INFO_CONTRACT);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
            }  if (rs != null && rs.next()) {
                String start_date = rs.getDate("start_date").toString();
                String expiration = rs.getDate("expiration").toString();
                int deposit = rs.getInt("deposit");
                int price = rs.getInt("price");

                contractInfor = Contract.builder()
                        .startDate(start_date)
                        .expiration(expiration)
                        .deposit(deposit)
                        .price(price)
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
        return contractInfor;
    }
}
