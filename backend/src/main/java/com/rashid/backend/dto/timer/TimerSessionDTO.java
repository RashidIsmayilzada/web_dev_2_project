package com.rashid.backend.dto.timer;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimerSessionDTO {
    private Long id;
    private Long taskId;
    private String taskTitle;
    private Long projectId;
    private String projectName;
    private LocalDateTime startedAt;
    private String description;
    private boolean active;
}
