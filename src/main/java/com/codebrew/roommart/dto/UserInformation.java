
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
    Date birthday;
    boolean sex;
    String phone;
    String address;
    String cccd;
}
