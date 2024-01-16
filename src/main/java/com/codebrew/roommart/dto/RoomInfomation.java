package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class RoomInfomation {
        private String property_name;
        private String address;
        private String ward;
        private String district;
        private String city;
}
