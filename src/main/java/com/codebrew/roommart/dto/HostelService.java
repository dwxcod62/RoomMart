package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HostelService {
    private int hostelServiceId;
    private int hostelID;
    private int serviceID;
    private int servicePrice;
    private String validDate;
    private int status;

    public HostelService(int hostelID, int serviceID, int servicePrice, String validDate, int status) {
        this.hostelID = hostelID;
        this.serviceID = serviceID;
        this.servicePrice = servicePrice;
        this.validDate = validDate;
        this.status = status;
    }

    public HostelService(int hostelID, int serviceID, int servicePrice) {
        this.hostelID = hostelID;
        this.serviceID = serviceID;
        this.servicePrice = servicePrice;
    }
}