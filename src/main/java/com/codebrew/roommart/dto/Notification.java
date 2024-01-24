package com.codebrew.roommart.dto;

import lombok.*;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notification {
    private int notification_id;
    private int account_id;
    private int land_id;
    private String title;
    private String content;
    private String createDate;
}
