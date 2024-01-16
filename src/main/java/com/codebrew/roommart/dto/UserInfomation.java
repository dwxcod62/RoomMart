package com.codebrew.roommart.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserInfomation {
    String fullname;
    String email;
    String birthday;
    int gender;
    String phone;
    String address;
    String identityCard;
}
