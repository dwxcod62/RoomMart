package com.codebrew.roommart.dto.OwnerDTO;

import com.codebrew.roommart.dto.Payment;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bill {
    int billID;
    int roomID;
    int totalMoney;
    String createdDate;
    String billTitle;
    String expiredPaymentDate;
    String paymentDate;
    int status;
    Payment payment;
}