package com.rashid.backend.dto.dashboard;

import com.rashid.backend.dto.report.MemberHoursDTO;
import lombok.Data;

import java.util.List;

@Data
public class TeamDashboardDTO {
    private Long teamId;
    private String teamName;
    private long totalProjects;
    private long totalTasks;
    private long completedTasks;
    private double totalHours;
    private List<MemberHoursDTO> memberHours;
}
