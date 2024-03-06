package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IBillDAO;
import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;
import com.codebrew.roommart.dto.Payment;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BillDAO implements IBillDAO {
    @Override
    public Bill getLastBill(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Bill bill = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT TOP 1 bill_id, total_money, created_date, bill_title, expired_payment_date, payment_date, status, Bill.payment_id as 'payment_id'\n" +
                        "FROM Bill, Payment\n" +
                        "WHERE room_id = ?\n" +
                        "ORDER BY created_date DESC";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int billID = rs.getInt("bill_id");
                    int totalMoney = rs.getInt("total_money");
                    String createdDate = rs.getString("created_date");
                    String billTitle = rs.getString("bill_title");
                    String expiredPaymentDate = rs.getString("expired_payment_date");
                    String paymentDate = rs.getString("payment_date");
                    int status = rs.getInt("status");
                    if (rs.getString("payment_id") == null) {
                        bill = new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(0, null));
                    } else {
                        int paymentID = rs.getInt("payment_id");
                        String paymentName = getPaymentName(paymentID);
                        bill = new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(paymentID, paymentName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return bill;
    }

    @Override
    public BillDetail getBillDetail(int billID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        BillDetail billDetail = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT bill_detail_id, consumeIDStart, consumeIDEnd, accountHostelOwnerID, accountRenterID \n" +
                        "FROM BillDetail WHERE bill_id = ?";

                pst = cn.prepareStatement(sql);
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
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return billDetail;
    }

    @Override
    public String getPaymentName(int paymentID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String paymentName = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT payment_name FROM Payment WHERE payment_id = ?";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, paymentID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    paymentName = rs.getString("payment_name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return paymentName;
    }

    @Override
    public ArrayList<Bill> GetListBillByHostel(String hostelName) {
        Bill bill = null;
        ArrayList<Bill> listBill = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //select * from [dbo].[Bill] where created_date BETWEEN  '2022-06-22' AND '2022-10-22'
        try {
            cn = DatabaseConnector.makeConnection();
            String Get_List_Bill_By_Hostel = "select A.bill_id, A.bill_title, A.created_date, A.expired_payment_date , A.payment_date,A.payment_id, A.room_id, A.status, A.total_money\n" +
                    "from Bill A join Rooms B on A.room_id = B.room_id join Hostels C on B.hostel_id = C.hostel_id\n" +
                    "where C.name = ?";
            ptm = cn.prepareStatement(Get_List_Bill_By_Hostel);
            ptm.setString(1, hostelName);
            rs = ptm.executeQuery();
            while (rs != null && rs.next()) {
                //A.bill_id, A.bill_title, A.created_date, A.expired_payment_date , A.payment_date, A.status, A.total_money
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
                listBill.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, rs);
        }
        return listBill;
    }

    @Override
    public Bill getBillByID(int billID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Bill bill = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "SELECT room_id, total_money, created_date, bill_title, expired_payment_date, payment_date, status, Bill.payment_id as 'payment_id'\n" +
                        "FROM Bill\n" +
                        "WHERE bill_id = ?\n";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, billID);

                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    int roomID = rs.getInt("room_id");
                    int totalMoney = rs.getInt("total_money");
                    String createdDate = rs.getString("created_date");
                    String billTitle = rs.getString("bill_title");
                    String expiredPaymentDate = rs.getString("expired_payment_date");
                    String paymentDate = rs.getString("payment_date");
                    int status = rs.getInt("status");
                    if (rs.getString("payment_id") == null) {
                        bill = new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(0, null));
                    } else {
                        int paymentID = rs.getInt("payment_id");
                        String paymentName = getPaymentName(paymentID);
                        bill = new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(paymentID, paymentName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return bill;
    }

    @Override
    public ArrayList<Bill> GetListBillByHostelYearQuater(String hostelName, String year, String quatertmp) {
        year = year == null ? "2022" : year;
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
        Bill bill = null;
        ArrayList<Bill> listBill = new ArrayList<>();
        Connection cn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cn = DatabaseConnector.makeConnection();
            String Get_List_Bill_By_Quarter_And_Hostel = "select A.bill_id, A.bill_title, A.created_date, A.expired_payment_date , A.payment_date,A.payment_id, A.room_id, A.status, A.total_money\n" +
                    "from [dbo].[Bill] A join [dbo].[Rooms] B on A.room_id = B.room_id join  [dbo].[Hostels] C on B.hostel_id = C.hostel_id\n" +
                    "where C.name = ? and A.created_date between ? and ?";
            ptm = cn.prepareStatement(Get_List_Bill_By_Quarter_And_Hostel);
            ptm.setString(1, hostelName);
            ptm.setString(2, startDate);
            ptm.setString(3, endDate);
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
                listBill.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, ptm, rs);
        }
        return listBill;
    }

    @Override
    public List<Bill> getListBillByRoomID(int roomID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Bill> bills = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String sql = "\n" +
                        "SELECT bill_id, total_money, created_date, bill_title, expired_payment_date, payment_date, status, payment_id\n" +
                        "FROM Bill\n" +
                        "WHERE room_id = ?\n" +
                        "ORDER BY created_date DESC";

                pst = cn.prepareStatement(sql);
                pst.setInt(1, roomID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int billID = rs.getInt("bill_id");
                        int totalMoney = rs.getInt("total_money");
                        String createdDate = rs.getString("created_date");
                        String billTitle = rs.getString("bill_title");
                        String expiredPaymentDate = rs.getString("expired_payment_date");
                        String paymentDate = rs.getString("payment_date");
                        int status = rs.getInt("status");
                        if (rs.getString("payment_id") == null) {
                            bills.add(new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(0, null)));
                        } else {
                            int paymentID = rs.getInt("payment_id");
                            String paymentName = getPaymentName(paymentID);
                            bills.add(new Bill(billID, roomID, totalMoney, createdDate, billTitle, expiredPaymentDate, paymentDate, status, new Payment(paymentID, paymentName)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return bills;
    }
}
