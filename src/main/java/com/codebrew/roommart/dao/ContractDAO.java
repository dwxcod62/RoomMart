package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractDAO {
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
                            .landOwnerId(ownerId)
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
}
