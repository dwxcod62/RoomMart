package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LandService {
    private int hostelServiceId;
    private int hostelID;
    private int serviceID;
    private int servicePrice;
    private String validDate;
    private int status;
}
