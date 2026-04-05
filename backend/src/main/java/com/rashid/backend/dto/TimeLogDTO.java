package com.rashid.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TimeLogDTO {
    private Long id;
    private Long taskId;
    private String taskTitle;
    private Long userId;
    private String username;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
}
