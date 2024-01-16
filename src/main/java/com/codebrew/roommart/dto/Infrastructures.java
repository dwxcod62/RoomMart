package com.codebrew.roommart.dto;

import lombok.*;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
    public class Infrastructures {
        private int infrastructureRoomId;
        private int roomId;
        private int infrastructureId;
        private int quantity;
        private int infrastructureStatus;
    }
