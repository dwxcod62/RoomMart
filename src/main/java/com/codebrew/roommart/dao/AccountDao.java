package com.codebrew.roommart.dao;

import com.codebrew.roommart.dto.Account;
import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.RoommateInfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    public Account getAccountByToken(String token){
        return null;
    }

    private Account getAccount(ResultSet rs) {
        Account acc = null;
        AccountInfo accInf = null;
        List<RoommateInfo> roommateInfoList = new ArrayList<>();
        try {
            int accId = rs.getInt("account_id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String createdate = rs.getString("create_date");
            int status = rs.getInt("status");
            int role = rs.getInt("role");
            int roomId = rs.getInt("room_id");
            if (role == 2) { //Renter
                roommateInfoList = new RoommateInfoDAO().getListRoommatesOfAnAccount(accId);
                accInf = getAccountInformationById(accId);
                acc = Account.builder()
                        .accId(accId)
                        .username(username)
                        .password(password)
                        .createDate(createdate)
                        .status(status)
                        .role(role)
                        .roomId(roomId)
                        .accountInfo(accInf)
                        .roommateInfo(roommateInfoList)
                        .build();
            } else {
                accInf = getAccountInformationById(accId);
                acc = Account.builder()
                        .accId(accId)
                        .username(username)
                        .password(password)
                        .createDate(createdate)
                        .status(status)
                        .role(role)
                        .roomId(-1)
                        .accountInfo(accInf)
                        .roommateInfo(null)
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acc;
    }
}
