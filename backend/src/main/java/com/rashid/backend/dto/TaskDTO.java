package com.rashid.backend.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String status;
    private Long projectId;
    private Long assigneeId;
    private String assigneeName;
}
