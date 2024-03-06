package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Payment;

import java.util.List;

public interface IPaymentDAO {
    public List<Payment> getPaymentList();
}
