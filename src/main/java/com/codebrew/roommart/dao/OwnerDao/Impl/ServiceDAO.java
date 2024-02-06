package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IServiceDAO;
import com.codebrew.roommart.dto.Services;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO implements IServiceDAO {
    private static final String GET_LIST_SERVICES_NOT_IN_HOSTEL =
            "SELECT service_id, service_name, unit FROM Services WHERE service_id NOT IN \n" +
                    "(SELECT DISTINCT(service_id) FROM HostelService WHERE hostel_id = ?)";
    @Override
    public List<Services> getListServicesNotInHostel(int hostelId) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Services> list = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_LIST_SERVICES_NOT_IN_HOSTEL);
                pst.setInt(1, hostelId);
                rs = pst.executeQuery();
                while (rs != null && rs.next()) {
                    list.add(Services.builder()
                            .serviceID(rs.getInt("service_id"))
                            .serviceName(rs.getString("service_name"))
                            .unit(rs.getString("unit")).build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return list;
    }
}
