package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IServiceInfoDAO;
import com.codebrew.roommart.dto.ServiceInfo;
import com.codebrew.roommart.dto.Services;
import com.codebrew.roommart.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceInfoDAO implements IServiceInfoDAO {
    private static final String GET_SERVICES_OF_HOSTEL =
            "SELECT N.hostel_service_id, S.service_id, service_name, valid_date, service_price, unit\n" +
                    "FROM Services S RIGHT JOIN HostelService N ON S.service_id = N.service_id\n" +
                    "WHERE N.status = 1 AND N.hostel_id = ?";

    @Override
    public List<ServiceInfo> getServicesOfHostel(int hostelID) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ServiceInfo> servicesList = new ArrayList<>();

        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_SERVICES_OF_HOSTEL);
                pst.setInt(1, hostelID);

                rs = pst.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        int hostelServiceId = rs.getInt("hostel_service_id");
                        int serviceID = rs.getInt("service_id");
                        String serviceName = rs.getString("service_name");
                        int servicePrice = rs.getInt("service_price");
                        String unit = rs.getString("unit");
                        String validDate = rs.getString("valid_date");
                        servicesList.add(new ServiceInfo(hostelServiceId, hostelID, serviceID, serviceName, validDate, servicePrice, unit));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicesList;
    }
}
