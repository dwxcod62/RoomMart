package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {
    private boolean status;
    private String content;
}
