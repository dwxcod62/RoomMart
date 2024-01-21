package com.codebrew.roommart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Room {
    private int roomId;
    private int propertyId;
    private int roomNumber;
    private int roomArea;
    private int attic;
    private int roomStatus;
    RoomInformation roomInformation;
}

