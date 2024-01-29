package com.codebrew.roommart.dto;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Invoice {
    int invoiceID;
    int roomID;
    int totalMoney;
    String createdDate;
    String invoiceTitle;
    String expiredPaymentDate;
    String paymentDate;
    boolean status;
    Payment payment;
}
