
package com.codebrew.roommart.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserInformation {
    String fullname;
    String email;
    String birthday;
    boolean sex;
    String phone;
    String address;
    String cccd;
    Information information;

    public UserInformation(String fullname, String email, String birthday, boolean sex, String phone, String address, String cccd) {
        this.fullname = fullname;
        this.email = email;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.cccd = cccd;
    }
}
