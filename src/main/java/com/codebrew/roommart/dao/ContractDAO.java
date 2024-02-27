package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractDAO {

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


    private static final String SELECT_contractInfo_By_AccountId_And_RoomId = "SELECT cm.c_status, cm.contract_id ,cd.* \n" +
            "FROM contract_main cm \n" +
            "JOIN contract_details cd ON cm.contract_details_id = cd.contract_details_id \n" +
            "WHERE cm.room_id = ? ";

    public JSONObject getContractInfoByAccountIdAndRoomId(int account_id, int role, int room_id){
        JSONObject jsonObject = new JSONObject();;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = SELECT_contractInfo_By_AccountId_And_RoomId + (role == 1 ? " and owner_id = ?" : (role == 2 ? " and renter_id = ?" : ""));

                pst = cn.prepareStatement(sql);
                pst.setInt(1, room_id);
                pst.setInt(2, account_id);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    jsonObject.put("current_day", rs.getDate("current_day").toString());

                    jsonObject.put("owner_full_name", rs.getString("owner_full_name"));
                    jsonObject.put("owner_phone", rs.getString("owner_phone"));
                    jsonObject.put("owner_identify_card", rs.getString("owner_identify_card"));
                    jsonObject.put("owner_address", rs.getString("owner_address"));
                    jsonObject.put("owner_birthday", rs.getDate("owner_birthday").toString());
                    jsonObject.put("owner_sign", rs.getString("owner_sign"));

                    jsonObject.put("renter_full_name", rs.getString("renter_full_name"));
                    jsonObject.put("renter_phone", rs.getString("renter_phone"));
                    jsonObject.put("renter_identify_card", rs.getString("renter_identify_card"));
                    jsonObject.put("renter_birthday", rs.getDate("renter_birthday").toString());
                    String renterSignValue = rs.getString("renter_sign");
                    if (renterSignValue == null) {
                        renterSignValue = "";
                    }
                    jsonObject.put("renter_sign", renterSignValue);


                    jsonObject.put("contract_status", rs.getInt("c_status"));
                    jsonObject.put("contract_id", rs.getInt("contract_id"));
                    jsonObject.put("room_start_date", rs.getDate("start_date").toString());
                    jsonObject.put("room_end_date", rs.getDate("end_date").toString());
                    jsonObject.put("room_fee", rs.getDouble("cost_per_month"));
                    jsonObject.put("payment_term", rs.getInt("month_per_pay"));
                    jsonObject.put("room_deposit", rs.getDouble("deposit"));
                }
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
        return jsonObject;
    }


    public void deleteContract(int room_id){
        Connection cn = null;
        PreparedStatement pst = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement("delete from contract_main where room_id = ?");
                pst.setInt(1, room_id);
                pst.executeUpdate();

                pst = cn.prepareStatement("update rooms set room_status = 0 where room_id = ?");
                pst.setInt(1, room_id);
                pst.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
    }

    private static final String
            GET_CONTRACT_BY_RENTER_ID = "SELECT contract_id, C.room_id, price, start_date, expiration, deposit, renter_id, hostel_owner_id\n" +
            "FROM Contracts AS C\n" +
            "JOIN Accounts AS A ON C.renter_id = A.account_id\n" +
            "WHERE A.account_id = ?";

    public UserInformation getOwnerByContractDetails(int renterId){
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserInformation accountInfor = null;
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

                accountInfor = UserInformation.builder()
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

    public UserInformation getContractByRenterId(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        UserInformation accountInfor = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_RENTER_BY_CONTRACT);
                pst.setInt(1, renterId);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullName = rs.getString("renter_full_name");
                    String phone = rs.getString("renter_phone");
                    String cccd = rs.getString("renter_identify_card");
                    String bod = rs.getDate("renter_birthday").toString();

                    accountInfor = UserInformation.builder()
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
    private static final String GET_CONTRACT_INFORMATION_BY_EMAIL = "SELECT cm.c_status, cm.contract_id , cd.* \n" +
            "FROM contract_main cm \n" +
            "JOIN contract_details cd ON cm.contract_details_id = cd.contract_details_id \n" +
            "WHERE cm.contract_id = ? ";

    public JSONObject getContractInformationByID(int contract_id){
        JSONObject jsonObject = new JSONObject();;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_CONTRACT_INFORMATION_BY_EMAIL);
                pst.setInt(1, contract_id);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    jsonObject.put("current_day", rs.getDate("current_day").toString());

                    jsonObject.put("owner_full_name", rs.getString("owner_full_name"));
                    jsonObject.put("owner_phone", rs.getString("owner_phone"));
                    jsonObject.put("owner_identify_card", rs.getString("owner_identify_card"));
                    jsonObject.put("owner_address", rs.getString("owner_address"));
                    jsonObject.put("owner_birthday", rs.getDate("owner_birthday").toString());
                    jsonObject.put("owner_sign", rs.getString("owner_sign"));

                    jsonObject.put("renter_full_name", rs.getString("renter_full_name"));
                    jsonObject.put("renter_phone", rs.getString("renter_phone"));
                    jsonObject.put("renter_identify_card", rs.getString("renter_identify_card"));
                    jsonObject.put("renter_birthday", rs.getDate("renter_birthday").toString());
                    String renterSignValue = rs.getString("renter_sign");
                    if (renterSignValue == null) {
                        renterSignValue = "";
                    }
                    jsonObject.put("renter_sign", renterSignValue);

                    jsonObject.put("contract_status", rs.getInt("c_status"));
                    jsonObject.put("contract_id", rs.getInt("contract_id"));
                    jsonObject.put("current_day", rs.getDate("current_day").toString());
                    jsonObject.put("room_start_date", rs.getDate("start_date").toString());
                    jsonObject.put("room_end_date", rs.getDate("end_date").toString());
                    jsonObject.put("room_fee", rs.getDouble("cost_per_month"));
                    jsonObject.put("payment_term", rs.getInt("month_per_pay"));
                    jsonObject.put("room_deposit", rs.getDouble("deposit"));
                }
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
        return jsonObject;
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
                String end_date = rs.getDate("end_date").toString();
                int deposit = rs.getInt("deposit");
                int cost_per_month = rs.getInt("cost_per_month");

                contractInfor = Contract.builder()
                        .startDate(start_date)
                        .expiration(end_date)
                        .deposit(deposit)
                        .price(cost_per_month)
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
