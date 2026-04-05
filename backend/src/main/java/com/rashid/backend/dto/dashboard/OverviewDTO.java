package com.rashid.backend.dto.dashboard;

import com.rashid.backend.dto.task.TaskDTO;
import com.rashid.backend.dto.timelog.TimeLogDTO;
import lombok.Data;

import java.util.List;

@Data
public class OverviewDTO {
    private DashboardStatsDTO stats;
    private List<TaskDTO> recentTasks;
    private List<TimeLogDTO> recentTimeLogs;
}
