package com.codebrew.roommart.dto;

import lombok.*;
import java.util.Date;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    private int roomId;
    private int propertyId;
    private int roomNumber;
//    private int capacity;
//    int roomStatus;
    private double roomArea;
    private int attic;
    private int roomStatus;
//    String inviteCode;
//    String QRCode;
//    Date expiredTimeCode;

    RoomInfomation roomInfomation;
}
