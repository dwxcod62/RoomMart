package com.codebrew.roommart.dto.OwnerDTO;

import com.codebrew.roommart.dto.AccountInfo;
import com.codebrew.roommart.dto.Report;
import com.codebrew.roommart.dto.ReportCategory;
import com.codebrew.roommart.dto.Room;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportDetail {
    private Report report;
    private ReportCategory category;
    private AccountInfo renterInformation;
    private Room room;
    private Hostel hostel;
}
