package com.rashid.backend.dto.report;

import lombok.Data;

import java.util.List;

@Data
public class CompletedTasksReportDTO {
    private Long teamId;
    private int totalCompletedTasks;
    private List<CompletedTaskItemDTO> tasks;
}
