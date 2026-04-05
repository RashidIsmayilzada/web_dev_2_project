package com.rashid.backend.dto.task;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String status;
    private Long projectId;
    private String projectName;
    private Long teamId;
    private Long assigneeId;
    private String assigneeName;
    private String assigneeUsername;
}
