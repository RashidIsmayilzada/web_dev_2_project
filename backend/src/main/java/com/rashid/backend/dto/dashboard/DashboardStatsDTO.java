package com.rashid.backend.dto.dashboard;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private long totalProjects;
    private long activeTasks;
    private long hoursTracked;
    private long totalTeams;
    private long pendingInvites;
}
