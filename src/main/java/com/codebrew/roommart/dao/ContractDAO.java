package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.utils.DatabaseConnector;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractDAO {


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

    public Contract getContractByRenterId(int accId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contract = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_CONTRACT_BY_RENTER_ID);
                pst.setInt(1, accId);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int contract_id = rs.getInt("contract_id");
                    int roomId = rs.getInt("room_id");
                    int price = rs.getInt("price");
                    String startDate = rs.getString("start_date");
                    String expiration = rs.getString("expiration");
                    int deposit = rs.getInt("deposit");
                    int renterId = rs.getInt("renter_id");
                    int ownerId = rs.getInt("hostel_owner_id");
                    contract = Contract.builder()
                            .contract_id(contract_id)
                            .room_id(roomId)
                            .price(price)
                            .startDate(startDate)
                            .expiration(expiration)
                            .deposit(deposit)
                            .renterId(renterId)
                            .hostelOwnerId(ownerId)
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
}
