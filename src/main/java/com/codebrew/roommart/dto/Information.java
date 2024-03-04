package com.codebrew.roommart.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Information {
    String fullname;
    String email;
    String birthday;
    int sex;
    String phone;
    String address;
    String cccd;
    int account_id;

    public Information(String fullname, String email, String birthday, int sex, String phone, String address, String cccd) {
        this.fullname = fullname;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.cccd = cccd;
    }
}
