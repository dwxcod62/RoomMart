package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.dto.Hostel;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;

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
            GET_INFO_CONTRACT = "SELECT cd.start_date, cd.end_date, cd.deposit, cd.cost_per_month\n" +
            "FROM contract_main cm\n" +
            "JOIN contract_details cd ON cm.contract_details_id = cd.contract_details_id\n" +
            "WHERE cm.renter_id = ?";
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

    public Hostel getHostelByContractDetails(int renterId){
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
