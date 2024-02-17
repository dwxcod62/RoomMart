package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.OwnerDTO.Bill;
import com.codebrew.roommart.dto.OwnerDTO.BillDetail;

public interface IBillDAO {
    public Bill getLastBill(int roomID);
    public BillDetail getBillDetail(int billID);
    public String getPaymentName(int paymentID);
}
