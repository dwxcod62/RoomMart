package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IHostelServiceDAO;
import com.codebrew.roommart.dto.HostelService;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HostelServiceDAO implements IHostelServiceDAO {
    private static final String GET_CURRENT_LIST_SERVICES_OF_A_HOSTEL =
            "SELECT hostel_service_id, hostel_id, service_id, service_price, valid_date, status \n" +
//                    "FROM HostelService WHERE status = 1 AND hostel_id = ?"; // chua check status
                    "FROM HostelService WHERE hostel_id = ?";

    private static final String INSERT_LIST_SERVICES_INTO_HOSTEL =
            "INSERT INTO HostelService (hostel_id, service_id, service_price, valid_date, status)\n" +
                    "VALUES (?, ?, ?, GETDATE(), 1)";

    private static final String UPDATE_STATUS_HOSTEL_SERVICES =
            "UPDATE HostelService SET status = ?\n" +
                    "WHERE hostel_service_id = ?";
    @Override
    public List<HostelService> getCurrentListServicesOfAHostel(int hostelId) {
        Connection cn = null;
        PreparedStatement psm = null;
        ResultSet rs = null;
        List<HostelService> list = new ArrayList<>();
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                psm = cn.prepareStatement(GET_CURRENT_LIST_SERVICES_OF_A_HOSTEL);
                psm.setInt(1, hostelId);
                rs = psm.executeQuery();
                while (rs != null && rs.next()) {
                    list.add(HostelService.builder()
                            .hostelServiceId(rs.getInt("hostel_service_id"))
                            .hostelID(rs.getInt("hostel_id"))
                            .serviceID(rs.getInt("service_id"))
                            .servicePrice(rs.getInt("service_price"))
                            .validDate(rs.getString("valid_date"))
                            .status(OwnerUtils.convertBooleanToInt(rs.getBoolean("status")))
                            .build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, psm, rs);
        }
        return list;
    }

    @Override
    public boolean updateStatusOfListHostelServices(boolean status, List<HostelService> hostelServiceList) {
        Connection conn = null;
        PreparedStatement psm = null;

        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                conn.setAutoCommit(false);

                boolean checkUpdate;
                for (HostelService item : hostelServiceList) {
                    psm = conn.prepareStatement(UPDATE_STATUS_HOSTEL_SERVICES);
                    psm.setBoolean(1, status);
                    psm.setInt(2, item.getHostelServiceId());
                    checkUpdate = psm.executeUpdate() > 0;

                    if (!checkUpdate) {
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return false;
                    }
                }
                conn.commit();
                conn.setAutoCommit(true);
                check = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, null);
        }
        return check;
    }

    @Override
    public boolean insertListServicesIntoHostel(List<HostelService> hostelServiceList, int hostelId) {
        Connection conn = null;
        PreparedStatement psm = null;

        boolean check = false;
        try {
            conn = DatabaseConnector.makeConnection();
            if (conn != null) {
                conn.setAutoCommit(false);

                boolean checkInsert;
                for (HostelService item : hostelServiceList) {
                    psm = conn.prepareStatement(INSERT_LIST_SERVICES_INTO_HOSTEL);
                    psm.setInt(1, hostelId);
                    psm.setInt(2, item.getServiceID());
                    psm.setDouble(3, item.getServicePrice());
                    checkInsert = psm.executeUpdate() > 0;

                    if (checkInsert) {
                        check =  true;
                    } else {
                        conn.rollback();
                        conn.setAutoCommit(true);
                        return false;
                    }
                }
                conn.commit();
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(conn, psm, null);
        }
        return check;
    }
}
