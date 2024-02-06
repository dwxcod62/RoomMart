package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Services;

import java.util.List;

public interface IServiceDAO {
    List<Services> getListServicesNotInHostel(int hostelId);
}
