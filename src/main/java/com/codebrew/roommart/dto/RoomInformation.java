package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomInformation {
    String property_name;
    String address;
    String ward;
    String district;
    String city;
}
