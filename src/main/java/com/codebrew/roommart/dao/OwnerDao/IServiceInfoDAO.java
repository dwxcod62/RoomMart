package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.ServiceInfo;

import java.util.List;

public interface IServiceInfoDAO {
    public List<ServiceInfo> getServicesOfHostel(int hostelID);
    public List<ServiceInfo> getServiceOfBill(int billDetailID, int hostelID);
}
