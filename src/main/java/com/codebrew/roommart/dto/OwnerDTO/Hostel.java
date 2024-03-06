package com.codebrew.roommart.dto.OwnerDTO;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Hostel {
    private int hostelID;
    private int hostelOwnerAccountID;
    private String hostelName;
    private String address;
    private String ward;
    private String district;
    private String city;

    public Hostel(int hostelOwnerAccountID, String hostelName, String address, String ward, String district, String city) {
        this.hostelOwnerAccountID = hostelOwnerAccountID;
        this.hostelName = hostelName;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.city = city;
    }
}
