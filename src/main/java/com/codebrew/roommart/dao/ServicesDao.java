package com.codebrew.roommart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codebrew.roommart.dto.Services;
import com.codebrew.roommart.utils.DatabaseConnector;

public class ServicesDao {


    private static final String GET_ALL_SERVICES = "SELECT service_id, service_name, unit FROM Services";

    private static final String GET_SERVICE_BY_NAME = "SELECT service_id, service_name, unit FROM Services WHERE service_name = ?";

    private static final String CREATE_NEW_SERVICE = "INSERT INTO Services(service_name, unit) VALUES(?, ?)";

    private static final String UPDATE_SERVICE = "UPDATE Services SET service_name = ?, unit = ? WHERE service_id = ?";
//Admin_Manage_Service
public Map<String, Services> getAll() {
    Connection cn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Map<String, Services> list = new HashMap<>();
    try {
        cn = DatabaseConnector.makeConnection();
        if (cn != null) {
            pst = cn.prepareStatement(GET_ALL_SERVICES);
            rs = pst.executeQuery();
            while (rs != null && rs.next()) {
                list.put(rs.getString("service_name"), Services.builder()
                        .serviceID(rs.getInt("service_id"))
                        .serviceName(rs.getString("service_name"))
                        .unit(rs.getString("unit")).build());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (cn != null) {
            try {
                cn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    return list;
}

    public Services getServiceByName(String serviceName) {
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Services service = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                pst = cn.prepareStatement(GET_SERVICE_BY_NAME);
                pst.setString(1, serviceName);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    service = Services.builder()
                            .serviceID(rs.getInt("service_id"))
                            .serviceName(rs.getString("service_name"))
                            .unit(rs.getString("unit")).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return service;
    }

    public boolean createNewService(Services services) {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean check = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                pst = cn.prepareStatement(CREATE_NEW_SERVICE);
                pst.setString(1, services.getServiceName());
                pst.setString(2, services.getUnit());
                check = pst.executeUpdate() > 0;
                if (!check) {
                    cn.rollback();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return check;
    }

    //Update service
    public boolean Update(Services services) {
        Connection cn = null;
        PreparedStatement pst = null;
        boolean check = false;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                cn.setAutoCommit(false);
                pst = cn.prepareStatement(UPDATE_SERVICE);
                pst.setString(1, services.getServiceName());
                pst.setString(2, services.getUnit());
                pst.setInt(3, services.getServiceID());
                check = pst.executeUpdate() > 0;
                if (!check) {
                    cn.rollback();
                }
                cn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return check;
    }
}
