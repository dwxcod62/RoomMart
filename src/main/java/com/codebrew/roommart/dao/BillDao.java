package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;
import com.codebrew.roommart.dto.Payment;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDao {

    private static final String GET_BILL_BY_RENTER_ID =
            "SELECT b.bill_id, b.total_money, b.created_date, b.bill_title, " +
                "b.expired_payment_date, b.payment_date, b.status, b.payment_id, b.room_id\n" +
                "FROM Bill b\n" +
                "INNER JOIN Contracts c ON b.room_id = c.room_id\n" +
                "WHERE c.renter_id = ?";

    private static final String GET_BILL_DETAIL =
            "SELECT bill_detail_id, consumeIDStart, consumeIDEnd, " +
                "accountHostelOwnerID, accountRenterID\n" +
                "FROM BillDetail\n" +
                "WHERE bill_id = ?";

    private static final String GET_RENTER_BILL_BY_ID =
            "SELECT bill_id, total_money, created_date, bill_title, " +
                    "expired_payment_date, payment_date, status, payment_id, room_id\n" +
                    "FROM Bill\n" +
                    "WHERE bill_id = ?";


    
    private static final String INSERT_NEW_BILL = "INSERT INTO Bill (total_money, created_date, bill_title, expired_payment_date, status, payment_id, room_id)\n" +
            "VALUES (?, GETDATE(), ?, ?, 0, NULL, ?)";
    private static final String INSERT_NEW_BILL_DETAIL = "INSERT INTO BillDetail (consumeIDStart, consumeIDEnd, accountHostelOwnerID, accountRenterID, bill_id)\n" +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_NEW_BILL_SERVICE = "INSERT INTO BillService (bill_detail_id, hostel_service_id) VALUES (?, ?)";

    private static final String INSERT_NEW_BILL_TAIL = "UPDATE Consumes SET status = 1 WHERE room_id = ?\n" +
            "INSERT INTO Consumes (number_electric, number_water, update_date, status, room_id) VALUES (?, ?, GETDATE(), 0, ?)\n";



    public List<Bill> getBllListByRenterID(int renterID) throws SQLException {
        List<Bill> billList = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cn = DatabaseConnector.makeConnection();
            ptm = cn.prepareStatement(GET_BILL_BY_RENTER_ID);
            ptm.setInt(1, renterID);
            rs = ptm.executeQuery();
            while (rs != null && rs.next()) {
                int billID = rs.getInt("bill_id");
                int totalMoney = rs.getInt("total_money");
                String createdDate = dateFormat.format(rs.getDate("created_date"));
                String billTitle = rs.getString("bill_title");
                String expiredPaymentDate = dateFormat.format(rs.getDate("expired_payment_date"));
                Date paymentDateTemp = rs.getDate("payment_date");
                String paymentDate;
                if (paymentDateTemp == null || paymentDateTemp.equals("")) {
                    paymentDate = "";
                } else {
                    paymentDate = dateFormat.format(paymentDateTemp);
                }
                int status = rs.getInt("status");
                int paymentID = rs.getInt("payment_id");
                int roomID = rs.getInt("room_id");
                billList.add(Bill.builder()
                        .billID(billID)
                        .createdDate(createdDate)
                        .totalMoney(totalMoney)
                        .billTitle(billTitle)
                        .expiredPaymentDate(expiredPaymentDate)
                        .paymentDate(paymentDate)
                        .status(status)
                        .payment(Payment.builder().paymentID(paymentID).build())
                        .roomID(roomID)
                        .build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return billList;
    }

     public String getBillTitle(int roomID, String contractStartDate) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String billTitle = null;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                String sql = "SELECT TOP 1 bill_title\n" +
                        "FROM Bill\n" +
                        "WHERE room_id = ?\n" +
                        "AND created_date > ?\n" +
                        "ORDER BY created_date DESC";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);
                pst.setString(2, contractStartDate);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    billTitle = rs.getString("bill_title");
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
        return billTitle;

    }

    public BillDetail getBillDetail(int billID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        BillDetail billDetail = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_BILL_DETAIL);
                pst.setInt(1, billID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int billDetailID = rs.getInt("bill_detail_id");
                    int consumeIDStart = rs.getInt("consumeIDStart");
                    int consumeIDEnd = rs.getInt("consumeIDEnd");
                    int accountHostelOwnerID = rs.getInt("accountHostelOwnerID");
                    int accountRenterID = rs.getInt("accountRenterID");
                    billDetail = new BillDetail(billDetailID, consumeIDStart, consumeIDEnd, accountHostelOwnerID, accountRenterID);
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
        return billDetail;
    }



    public boolean InsertANewBill(int totalMoney, String billTitle, String expiredPaymentDate, int roomID,
                                  int consumeIDStart, int consumeIDEnd, int accountHostelOwner, int accountRenterID,
                                  int numberLastElectric, int numberLastWater,
                                  ArrayList<Integer> hostelServiceList) {
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check = false;
        try {
            cn = DBUtils.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);

                int billID = 0;
                int billDetailID = 0;

                // Insert new Bill
                ptm = cn.prepareStatement(INSERT_NEW_BILL, Statement.RETURN_GENERATED_KEYS);
                ptm.setInt(1, totalMoney);
                ptm.setString(2, billTitle);
                ptm.setString(3, expiredPaymentDate);
                ptm.setInt(4, roomID);
                check = ptm.executeUpdate() > 0;

                rs = ptm.getGeneratedKeys();

                if (rs.next()) {
                    billID = rs.getInt(1);
                }

                // Insert Bill Detail
                ptm = cn.prepareStatement(INSERT_NEW_BILL_DETAIL, Statement.RETURN_GENERATED_KEYS);
                ptm.setInt(1, consumeIDStart);
                ptm.setInt(2, consumeIDEnd);
                ptm.setInt(3, accountHostelOwner);
                ptm.setInt(4, accountRenterID);
                ptm.setInt(5, billID);
                check = ptm.executeUpdate() > 0;

                rs = ptm.getGeneratedKeys();
                if (rs.next()) {
                    billDetailID = rs.getInt(1);
                }

                // Insert Many Bill_Service
                ptm = cn.prepareStatement(INSERT_NEW_BILL_SERVICE);
                for (Integer hostelServiceID : hostelServiceList) {
                    ptm.setInt(1, billDetailID);
                    ptm.setInt(2, hostelServiceID);
                    if (ptm.executeUpdate() == 0) {
                        check = false;
                        break;
                    }
                }

                // Do the rest of insert app
                ptm = cn.prepareStatement(INSERT_NEW_BILL_TAIL);
                ptm.setInt(1, roomID);
                ptm.setInt(2, numberLastElectric);
                ptm.setInt(3, numberLastWater);
                ptm.setInt(4, roomID);
                check = ptm.executeUpdate() > 0;

                if (!check) {
                    cn.rollback();
                } else {
                    cn.commit();
                }
                cn.setAutoCommit(true);
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
            if (ptm != null) {
                try {
                    ptm.close();
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
        return check;
    }

   public Bill getRenterBillByID(int billID) throws SQLException {
        Bill bill = null;
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cn = DBUtils.makeConnection();
            ptm = cn.prepareStatement(GET_RENTER_BILL_BY_ID);
            ptm.setInt(1, billID);
            rs = ptm.executeQuery();
            if (rs != null && rs.next()) {
                int totalMoney = rs.getInt("total_money");
                String createdDate = dateFormat.format(rs.getDate("created_date"));
                String billTitle = rs.getString("bill_title");
                String expiredPaymentDate = dateFormat.format(rs.getDate("expired_payment_date"));
                Date paymentDateTemp = rs.getDate("payment_date");
                String paymentDate;
                if (paymentDateTemp == null || paymentDateTemp.equals("")) {
                    paymentDate = "";
                } else {
                    paymentDate = dateFormat.format(paymentDateTemp);
                }
                int status = rs.getInt("status");
                int paymentID = rs.getInt("payment_id");
                int roomID = rs.getInt("room_id");
                bill = Bill.builder()
                        .billID(billID)
                        .createdDate(createdDate)
                        .totalMoney(totalMoney)
                        .billTitle(billTitle)
                        .expiredPaymentDate(expiredPaymentDate)
                        .paymentDate(paymentDate)
                        .status(status)
                        .payment(Payment.builder().paymentID(paymentID).build())
                        .roomID(roomID)
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return bill;
    }
}
