package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {
    private static final String GET_INVOICE_LIST_BY_OWNER_ACCOUNT_ID =
            "SELECT DISTINCT bill_id, bill_title, Rooms.room_id AS room_id, total_money, created_date\n" +
                    "FROM Bill, Rooms, Hostels\n" +
                    "WHERE Bill.room_id = Rooms.room_id\n" +
                    "AND Hostels.owner_account_id = ?\n" +
                    "AND Bill.room_id = Rooms.room_id\n" +
                    "AND Rooms.hostel_id = Hostels.hostel_id\n" +
                    "AND Bill.status = ?\n" +
                    "ORDER BY created_date DESC";

    public List<Bill> getInvoiceListByOwnerAccountID(int ownerAccountID, int status) throws SQLException {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Bill> invoiceList = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_INVOICE_LIST_BY_OWNER_ACCOUNT_ID);
                pst.setInt(1, ownerAccountID);
                pst.setInt(2, status);
                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int billID = rs.getInt("bill_id");
                        String billTitle = rs.getString("bill_title");
                        int roomID = rs.getInt("room_id");
                        int totalMoney = rs.getInt("total_money");
                        String createdDate = rs.getString("created_date");
                        Bill bill = Bill.builder().billID(billID)
                                .billTitle(billTitle)
                                .roomID(roomID)
                                .totalMoney(totalMoney)
                                .createdDate(createdDate).build();
                        invoiceList.add(bill);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return invoiceList;
    }
}
