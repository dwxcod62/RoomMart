package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.Services;

import java.util.List;
import java.util.Map;

public interface IServiceDAO {
    List<Services> getListServicesNotInHostel(int hostelId);
    public Map<String, Services> getAll();
}
