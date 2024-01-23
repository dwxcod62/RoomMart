package com.codebrew.roommart.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Land {
    private int landID;
    private int landOwnerAccountID;
    private String landName;
    private String address;
    private String ward;
    private String district;
    private String city;
}
