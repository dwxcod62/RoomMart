package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IContractDAO;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
                    contract = Contract.builder()
                            .contract_id(rs.getInt("contract_id"))
                            .deposit(rs.getInt("deposit"))
                            .expiration(rs.getDate("expiration").toString())
                            .hostelOwnerId(rs.getInt("hostel_owner_id"))
                            .price(rs.getInt("price"))
                            .renterId(rs.getInt("renter_id"))
                            .room_id(rs.getInt("room_id"))
                            .startDate(rs.getDate("start_date").toString())
                            .status(rs.getInt("status"))
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
