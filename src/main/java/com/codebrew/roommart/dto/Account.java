package com.codebrew.roommart.dto;

import java.sql.Date;
import java.util.List;

import lombok.*;


@Builder
@AllArgsConstructor
@Getter
@Setter
public class Account {
    private int accountId;
    private String username;
    private String password;
    private String token;
    private Date createDate;
    private int status;
    private int role;

    private Integer roomId; 
    private Date expiredDate;
    private UserInformation accountInfo;
    private List<Roomate> roommateInfo;
    private String otp;
    private Date expiredTimeOTP;

    public Account(String username, String password,String token, int status, int role, Integer roomId, Date expiredDate, UserInformation accountInfo, List<Roomate> roommateInfo, String otp, Date expiredTimeOTP) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.status = status;
        this.role = role;
        this.roomId = roomId;
        this.expiredDate = expiredDate;
        this.accountInfo = accountInfo;
        this.roommateInfo = roommateInfo;
        this.otp = otp;
        this.expiredTimeOTP = expiredTimeOTP;
    }
}

