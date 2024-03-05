package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;

import java.util.ArrayList;

public interface IBillDAO {
    public Bill getLastBill(int roomID);
    public BillDetail getBillDetail(int billID);
    public String getPaymentName(int paymentID);
    public ArrayList<Bill> GetListBillByHostel(String hostelName);

    public Bill getBillByID(int billID);
    public ArrayList<Bill> GetListBillByHostelYearQuater(String hostelName,  String year, String quatertmp);
}
