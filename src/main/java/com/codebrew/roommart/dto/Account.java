package com.codebrew.roommart.dto;

import java.sql.Date;
import java.util.List;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    private int accId;
    private String email;
    private String password;
    private String token;
    private String createDate;
    private int status;
    private int role;
    private UserInformation accountInfo;
}

