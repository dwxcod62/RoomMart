package com.codebrew.roommart.dao.OwnerDao;

import com.codebrew.roommart.dto.OwnerDTO.ReportDetail;

import java.util.List;

public interface IReportDetailDAO {
    public List<ReportDetail> getListReports(int hostelOwnerId, int status);
}
