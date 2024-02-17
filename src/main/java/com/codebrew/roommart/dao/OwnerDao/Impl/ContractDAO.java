package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IContractDAO;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractDAO implements IContractDAO {
    @Override
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
                        "WHERE room_id = ? AND status = 1";

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
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return contract;
    }
}
