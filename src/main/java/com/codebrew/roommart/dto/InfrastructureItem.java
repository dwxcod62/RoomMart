package com.codebrew.roommart.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString

public class InfrastructureItem {
    int idInfrastructureItem;
    String infrastructureName;
    String infrastuctureUnit;
}
