package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Contract {
    int contract_id;
    int room_id;
    int price;
    String startDate;
    String expiration;
    int deposit;
    int landOwnerId;
    int renterId;
    int status;
    String cancelDate;
}