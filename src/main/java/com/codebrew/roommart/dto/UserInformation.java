package com.codebrew.roommart.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserInformation {
    private String fullname;
    private String birthday;
    private boolean sex;
    private String phone;
    private String address;
    private String cccd;
}
