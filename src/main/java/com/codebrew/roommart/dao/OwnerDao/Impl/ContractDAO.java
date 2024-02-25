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
                String sql = "SELECT *\n" +
                        "FROM contract_main\n" +
                        "INNER JOIN contract_details ON contract_main.contract_details_id = contract_details.contract_details_id\n" +
                        "WHERE contract_main.room_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    contract = Contract.builder()
                            .contract_id(rs.getInt("contract_id"))
                            .deposit(rs.getInt("deposit"))
                            .expiration(rs.getDate("end_date").toString())
                            .hostelOwnerId(rs.getInt("owner_id"))
                            .price(rs.getInt("cost_per_month"))
                            .renterId(rs.getInt("renter_id"))
                            .room_id(rs.getInt("room_id"))
                            .startDate(rs.getDate("start_date").toString())
                            .status(rs.getInt("c_status"))
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
