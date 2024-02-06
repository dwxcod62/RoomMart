package com.codebrew.roommart.dto.OwnerDTO;

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
