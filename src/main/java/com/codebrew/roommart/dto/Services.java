package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Services {
    private int serviceID;
    private String serviceName;
    private String unit;
}
