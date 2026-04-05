package com.rashid.backend.dto.report;

import lombok.Data;

import java.util.List;

@Data
public class TeamHoursDTO {
    private Long teamId;
    private String teamName;
    private double totalHours;
    private List<MemberHoursDTO> memberHours;
}
