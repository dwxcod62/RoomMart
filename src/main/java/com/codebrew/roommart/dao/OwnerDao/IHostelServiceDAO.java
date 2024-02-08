package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.HostelService;

import java.util.List;

public interface IHostelServiceDAO {
    List<HostelService> getCurrentListServicesOfAHostel (int hostelId);
    boolean updateStatusOfListHostelServices(boolean status, List<HostelService> hostelServiceList);

    boolean insertListServicesIntoHostel(List<HostelService> hostelServiceList, int hostelId);
}
