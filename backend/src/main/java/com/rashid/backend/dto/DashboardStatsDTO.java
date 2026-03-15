package com.rashid.backend.dto;

import lombok.Data;

@Data
public class DashboardStatsDTO {
    private long totalProjects;
    private long activeTasks;
    private long hoursTracked;
}
