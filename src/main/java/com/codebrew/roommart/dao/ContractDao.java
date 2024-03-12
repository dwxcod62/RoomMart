package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.*;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
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
            GET_HOSTEL_BY_CONTRACT = "SELECT h.hostel_id, h.owner_account_id, h.name, h.address, h.ward, h.district, h.city " +
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
            GET_INFRASTRUCTURES_BY_CONTRACT = "SELECT infrastructureitem.infrastructure_name, " +
            "infrastructuresroom.quantity, infrastructuresroom.status\n" +
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
            GET_INFO_CONTRACT = "SELECT start_date, expiration, deposit, price, renter_sign, owner_sign\n" +
            "FROM Contracts\n" +
            "WHERE renter_id = ?";

    private static final String
            GET_CONTRACT_BY_RENTER_ID = "SELECT *\n" +
            "FROM Contracts\n" +
            "WHERE renter_id = ? and status = -1";

    private static final String ADD_AN_CONTRACT_OWNER =
            "INSERT INTO [dbo].[Contracts]([room_id], [price], [start_date], [expiration], [deposit], [hostel_owner_id], [renter_id], [status], [owner_sign])\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_CONTRACT_STATUS =
            "UPDATE Contracts SET status = 1, cancelDate = GETDATE()\n" +
                    "WHERE room_id = ? AND renter_id = ? AND status = 0";


    private static final String INSERT_NEW_CONTRACT = "insert into Contracts (room_id, price, start_date, expiration, deposit, hostel_owner_id, renter_id, status, cancelDate, renter_sign, owner_sign )\n" +
            "values(?,?,?,?,?,?,?,?,?,?,?)";


    public int countResgiterContractByRenterId(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("select count(*) as 'count' from Contracts where status = -1 and renter_id = ?");
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    count = rs.getInt("count");
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


    public boolean insertContract(Contract contract, Integer price){
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                pst = cn.prepareStatement(INSERT_NEW_CONTRACT);

                pst.setInt(1, contract.getRoom_id());
                pst.setInt(2, price);
                pst.setString(3, contract.getStartDate());
                pst.setString(4, contract.getExpiration());
                pst.setInt(5, contract.getDeposit());
                pst.setInt(6, contract.getHostelOwnerId());
                pst.setInt(7, contract.getRenterId());
                pst.setInt(8, contract.getStatus());
                pst.setString(9, contract.getCancelDate());
                pst.setString(10, contract.getRenter_sign());
                pst.setString(11, contract.getOwner_sign());

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

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
                int hostel_id = rs.getInt("hostel_id");
                int ownerid = rs.getInt("owner_account_id");
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
                        .hostelID(hostel_id)
                        .hostelOwnerAccountID(ownerid)
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
                    int status = rs.getInt("status");

                    Infrastructures infrastructures = Infrastructures.builder()
                            .name(name)
                            .quantity(quantity)
                            .status(status)
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
                String renter_sign = rs.getString("renter_sign");
                String owner_sign = rs.getString("owner_sign");

                contractInfor = Contract.builder()
                        .startDate(start_date)
                        .expiration(expiration)
                        .deposit(deposit)
                        .price(price)
                        .renter_sign(renter_sign)
                        .owner_sign(owner_sign)
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

    public boolean addContractOwner(Contract contract) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                pst = cn.prepareStatement(ADD_AN_CONTRACT_OWNER);

                pst.setInt(1, contract.getRoom_id());
                pst.setDouble(2, contract.getPrice());
                pst.setString(3, contract.getStartDate());
                pst.setString(4, contract.getExpiration());
                pst.setDouble(5, contract.getDeposit());
                pst.setInt(6, contract.getHostelOwnerId());
                pst.setInt(7, contract.getRenterId());
                pst.setInt(8, contract.getStatus());
                pst.setString(9, contract.getOwner_sign());

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public boolean checkAccountInContract(Contract contract) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                pst = cn.prepareStatement(ADD_AN_CONTRACT_OWNER);

                pst.setInt(1, contract.getRoom_id());
                pst.setDouble(2, contract.getPrice());
                pst.setString(3, contract.getStartDate());
                pst.setString(4, contract.getExpiration());
                pst.setDouble(5, contract.getDeposit());
                pst.setInt(6, contract.getHostelOwnerId());
                pst.setInt(7, contract.getRenterId());
                pst.setInt(8, contract.getStatus());
                pst.setString(9, contract.getOwner_sign());

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public Contract getContractByUserId(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contract = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_CONTRACT_BY_RENTER_ID);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
            }  if (rs != null && rs.next()) {
                contract = Contract.builder()
                        .contract_id(rs.getInt("contract_id"))
                        .room_id(rs.getInt("room_id"))
                        .price(rs.getInt("price"))
                        .startDate(rs.getDate("start_date").toString())
                        .expiration(rs.getDate("expiration").toString())
                        .deposit(rs.getInt("deposit"))
                        .hostelOwnerId(rs.getInt("hostel_owner_id"))
                        .renterId(rs.getInt("renter_id"))
                        .renter_sign(rs.getString("renter_sign"))
                        .owner_sign(rs.getString("owner_sign"))
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
        return contract;
    }

    public boolean addContractRenter(int contract_id, String sign) {
        boolean check = false;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                pst = cn.prepareStatement("Update [Contracts] set renter_sign = ? , status = 0 where contract_id = ?");
                pst.setString(1, sign);
                pst.setInt(2, contract_id);

                if (pst.executeUpdate() > 0) {
                    check = true;
                    cn.setAutoCommit(true);
                } else {
                    cn.rollback();
                    cn.setAutoCommit(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

    public Contract getContract(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contract = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT contract_id, room_id, price, start_date, expiration, deposit, hostel_owner_id, renter_id, status\n" +
                        "FROM Contracts\n" +
                        "WHERE room_id = ? AND status = 0";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int contract_id = rs.getInt("contract_id");
                    int price = rs.getInt("price");
                    String startDate = rs.getString("start_date");
                    String expiration = rs.getString("expiration");
                    int deposit = rs.getInt("deposit");
                    int hostelAccountId = rs.getInt("hostel_owner_id");
                    int renterAccountId = rs.getInt("renter_id");
                    int status = rs.getInt("status");
                    contract = Contract.builder()
                            .contract_id(contract_id)
                            .room_id(roomID)
                            .price(price)
                            .startDate(startDate)
                            .expiration(expiration)
                            .deposit(deposit)
                            .hostelOwnerId(hostelAccountId)
                            .renterId(renterAccountId)
                            .status(status)
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
        return contract;
    }

    public boolean updateContractStatus (int roomId, int renterAccountId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {

                pst = cn.prepareStatement(UPDATE_CONTRACT_STATUS);
                // Return key Identity of data just inserted
                pst.setInt(1, roomId);
                pst.setInt(2, renterAccountId);

                check = pst.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return check;
    }

}
