package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HandlerStatus {
    private boolean status;
    private String content;
}
