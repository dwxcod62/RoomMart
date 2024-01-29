package com.codebrew.roommart.dto;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    int paymentID;
    String paymentName;
}
