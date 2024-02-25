package com.codebrew.roommart.dao.OwnerDao.Impl;

import com.codebrew.roommart.dao.OwnerDao.IUserInfoDAO;
import com.codebrew.roommart.dto.UserInformation;
import com.codebrew.roommart.utils.DatabaseConnector;
import com.codebrew.roommart.utils.OwnerUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserInfoDAO implements IUserInfoDAO {
    @Override
    public UserInformation getAccountInformationById(int renterId) {
        Connection cn = null;
        PreparedStatement pst = null;
        UserInformation inf = null;
        ResultSet rs = null;
        try {
            cn = DatabaseConnector.makeConnection();
            if (cn != null) {
                String GET_RENTER_INFO_BY_ID = "SELECT * FROM AccountInformations WHERE account_id = ?";
                pst = cn.prepareStatement(GET_RENTER_INFO_BY_ID);
                pst.setInt(1, renterId);
                rs = pst.executeQuery();
                if (rs != null && rs.next()) {
                    String fullname = rs.getString("fullname");
                    String email = rs.getString("email");
                    String birthday = rs.getString("birthday");
                    boolean sex = OwnerUtils.convertIntToBoolean(rs.getInt("sex"));
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String cccd = rs.getString("identity_card_number");
                    inf = new UserInformation(fullname, email, birthday, sex, phone, address, cccd);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            OwnerUtils.closeSQL(cn, pst, rs);
        }
        return inf;
    }
}
