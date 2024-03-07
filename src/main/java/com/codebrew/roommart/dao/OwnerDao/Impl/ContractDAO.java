package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IContractDAO;
import com.codebrew.roommart.dto.Contract;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
                        "WHERE room_id = ? AND status = 0";

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

    @Override
    public ArrayList<Contract> GetListContractByHostelYearQuater(String hostelName, String year, String quatertmp) {
        year = year == null ? "2022" : year;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Contract contract = null;
        ArrayList<Contract> listContract = new ArrayList<>();
        String startDate;
        String endDate;
        int quater = 0;
        if (quatertmp.equals("quater_1"))
            quater = 1;
        if (quatertmp.equals("quater_2"))
            quater = 2;
        if (quatertmp.equals("quater_3"))
            quater = 3;
        if (quatertmp.equals("quater_4"))
            quater = 4;

        if (quater == 1){
            startDate = year + "/01/01";
            endDate = year + "/03/31";
        } else if (quater == 2) {
            startDate = year + "/04/01";
            endDate = year + "/06/30";
        } else if (quater == 3) {
            startDate = year + "/07/01";
            endDate = year + "/9/30";
        }else if (quater == 4){
            startDate = year + "/10/01";
            endDate = year + "/12/31";
        }else{
            startDate = year + "/01/01";
            endDate = year + "/12/31";
        }
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "select A.contract_id, A.room_id, A.price, A.start_date, A.expiration, A.deposit, A.renter_id, A.hostel_owner_id , A.status, A.cancelDate \n" +
                        "from [dbo].[Contracts] A join [dbo].[Rooms] B on A.room_id = B.room_id join [dbo].[Hostels] C on B.hostel_id = C.hostel_id\n" +
                        "where C.name = ? and A.start_date between ? and ?";
                pst = cn.prepareStatement(sql);
                pst.setString(1, hostelName);
                pst.setString(2, startDate);
                pst.setString(3, endDate);

                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    int contract_id = rs.getInt("contract_id");
                    int roomId = rs.getInt("room_id");
                    int price = rs.getInt("price");
                    String startDate1 = rs.getString("start_date");
                    String expiration = rs.getString("expiration");
                    int deposit = rs.getInt("deposit");
                    int renterId = rs.getInt("renter_id");
                    int ownerId = rs.getInt("hostel_owner_id");
                    String cancelDate = rs.getString("cancelDate");
                    contract = Contract.builder()
                            .contract_id(contract_id)
                            .room_id(roomId)
                            .price(price)
                            .startDate(startDate1)
                            .expiration(expiration)
                            .deposit(deposit)
                            .renterId(renterId)
                            .hostelOwnerId(ownerId)
                            .cancelDate(cancelDate)
                            .build();
                    listContract.add(contract);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return listContract;
    }
}
