
package com.codebrew.roommart.dto;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Roommate {
    private int roommateID;
    private UserInformation information;
    private String parentName;
    private String parentPhone;
}
