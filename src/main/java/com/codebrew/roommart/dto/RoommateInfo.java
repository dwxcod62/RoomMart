
package com.codebrew.roommart.dto;

import lombok.*;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class RoommateInfo {
    private int roommateID;
    private UserInfomation information;
    private String parentName;
    private String parentPhone;
}
